package com.ad.web.service.Impl;

import com.ad.web.common.Enum.GraphType;
import com.ad.web.entity.AdPo;
import com.ad.web.entity.Category;
import com.ad.web.entity.Graph;
import com.ad.web.entity.vo.graph.GraphVo;
import com.ad.web.exception.AdWebException;
import com.ad.web.mapper.AdPoMapper;
import com.ad.web.entity.vo.adpo.AdPoVo;
import com.ad.web.service.AdPoClickCounterService;
import com.ad.web.service.AdPoService;
import com.ad.web.service.CategoryService;
import com.ad.web.service.GraphService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class AdPoServiceImpl implements AdPoService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GraphService graphService;
    @Autowired
    private AdPoMapper adPoMapper;
    @Autowired
    private AdPoClickCounterService adPoClickCounterService;

    @Override
    public List<AdPo> getAllAdPo() {
        return adPoMapper.getAllAdPo();
    }

    @Value("${adWeb.indexChoseAdPoNum}")
    private int choseAdPo;
    @Override
    public List<AdPoVo> getSomeAdPoVoOrderByClickNum() {
        List<AdPo> adPoList = adPoMapper.getAdPoOrderByClickNum(choseAdPo);
        return adPoListToVoList(adPoList);
    }

    @Override
    public List<AdPoVo> adPoListToVoList(List<AdPo> adPoList) {
        if (adPoList == null || adPoList.isEmpty())return List.of();

        List<Long> adPoIds = adPoList.stream().map(AdPo::getId).toList();
        Map<Long, List<String>> categoriesMap  = batchGetCategoriesByAdPoIds(adPoIds);
        Map<Long, List<String>> graphUrlsMap = batchGetGraphUrlByAdPoIds(adPoIds);

        return adPoList.stream()
                .map(adPo -> {
                    AdPoVo adPoVo = new AdPoVo();
                    adPoVo.setInformation(adPo);
                    adPoVo.setImage_src(graphUrlsMap.getOrDefault(adPo.getId(),List.of()));
                    adPoVo.setCategories(categoriesMap.getOrDefault(adPo.getId(),List.of()));
                    return adPoVo;
                })
                .toList();
    }
    private Map<Long,List<String>> batchGetCategoriesByAdPoIds(List<Long> adPoIds){
        try {
            return categoryService.batchGetCategoriesByAdPoIds(adPoIds)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().stream().map(Category::getName).toList()
                    ));
        } catch (Exception ex) {
            log.error("Failed to batch get categories", ex);
            return Map.of();
        }
    }
    private Map<Long, List<String>> batchGetGraphUrlByAdPoIds(List<Long> adPoIds){
        try {
            return graphService.batchGetGraphUrlByAdPoIds(GraphType.ADVERTISEMENT_POSITION_GRAPH.getNum(),adPoIds)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().stream().map(Graph::getUrl).toList()
                            )
                    );
        } catch (Exception ex) {
            log.error("Failed to batch get graphUrl", ex);
            return Map.of();
        }
    }
    @Override
    public List<AdPoVo> getAdPoListByKeyWord(String keyWord) {
        // 1. 用于存储最终结果（自动去重）
        Set<AdPo> combineResults = new LinkedHashSet<>();  // 保持顺序

// 2. 用于记录已匹配的广告ID（避免重复查询）
        Set<Long> matchedAdPoIds = new HashSet<>();

// 3. 分步搜索（按优先级顺序）
        List<Supplier<List<AdPo>>> searchSteps = List.of(
                () -> adPoMapper.getAdPoListKeyToName(keyWord),          // 第一步：按 name 搜索
                () -> adPoMapper.getAdPoListKeyToContent(keyWord),      // 第二步：按 content 搜索（排除已匹配的）
                () -> adPoMapper.getAdPoListKeyToPlatform(keyWord),     // 第三步：按 platform 搜索（排除已匹配的）
                () -> adPoMapper.getAdPoListKeyToType(keyWord),         // 第四步：按 type 搜索（排除已匹配的）
                () -> categoryService.getCategoriesAdPoIdsKeyToName(keyWord)  // 第五步：按 category 搜索
                        .stream()
                        .filter(adPoId -> !matchedAdPoIds.contains(adPoId))  // 排除已匹配的
                        .map(adPoMapper::selectByPrimaryKey)
                        .filter(Objects::nonNull)
                        .toList()
        );

// 4. 按顺序执行搜索，并动态排除已匹配的广告
        for (Supplier<List<AdPo>> step : searchSteps) {
            List<AdPo> currentResults = step.get();
            currentResults.stream()
                    .filter(adPo -> !matchedAdPoIds.contains(adPo.getId()))  // 避免重复添加
                    .forEach(adPo -> {
                        combineResults.add(adPo);             // 添加到最终结果
                        matchedAdPoIds.add(adPo.getId());     // 记录已匹配的ID
                    });
        }

// 5. 转换为 List（如果需要）
        return adPoListToVoList(new ArrayList<>(combineResults));
//        List<AdPo> combineResults = new java.util.ArrayList<>(Stream.of(
//                        adPoMapper.getAdPoListKeyToName(keyWord),
//                        adPoMapper.getAdPoListKeyToContent(keyWord),
//                        adPoMapper.getAdPoListKeyToPlatform(keyWord),
//                        adPoMapper.getAdPoListKeyToType(keyWord),
//                        categoryService.getCategoriesAdPoIdsKeyToName(keyWord)
//                                .stream()
//                                .map(adPoId -> adPoMapper.selectByPrimaryKey(adPoId))
//                                //.filter(Objects::nonNull)
//                                .toList()
//                )
//                .filter(Objects::nonNull)
//                .flatMap(List::stream)
//                .distinct()//去重
//                .toList());
//        if (combineResults.isEmpty()){
//            List<AdPo> adPoListKeyToCategory = categoryService.getCategoriesAdPoIdsKeyToName(keyWord)
//                    .stream()
//                    .map(adPoId -> adPoMapper.selectByPrimaryKey(adPoId))
//                    .filter(Objects::nonNull)
//                    .toList();
//            if (adPoListKeyToCategory.isEmpty())throw new AdWebException(404,"未找到");
//            else return adPoListToVoList(adPoListKeyToCategory);
//        }else {
             //对搜索结果按匹配字段的优先级排序
//            combineResults.sort(Comparator.comparingInt(
//                    adPo->{
//                        int score = 0;
//                        if (adPo.getName().contains(keyWord)) score += 4;
//                        if (adPo.getContent().contains(keyWord)) score += 3;
//                        if (adPo.getPlatform().contains(keyWord)) score += 2;
//                        if (adPo.getType().contains(keyWord)) score += 1;
//                        return -score;
//                    }
//            ));
//            return adPoListToVoList(combineResults);
        //}
    }

    @Override
    public AdPoVo getDetailById(Long adPoId) {
        AdPo adPo = adPoMapper.selectByPrimaryKey(adPoId);
        return convertToAdPoVo(adPo);
    }

    // 使用Spring管理的线程池
    @Autowired
    @Qualifier("taskExecutor")
    private Executor asyncTaskExecutor;


    AdPoVo convertToAdPoVo(AdPo adPo){
        if (adPo == null)return null;

        // 并行获取图片和分类信息
        CompletableFuture<List<String>> graphUrlsFuture = CompletableFuture.supplyAsync(() -> graphService
                                .getGraphListByTypeAndId(GraphType.ADVERTISEMENT_POSITION_GRAPH.getNum(), adPo.getId())
                        , asyncTaskExecutor)
                .exceptionally(ex -> {
                    log.error("Failed to get graphUrl for adPo: {}", adPo.getId(), ex);
                    return List.of();
                })
                .thenApply(graphVos ->
                        graphVos.stream().map(GraphVo::getUrl).toList()
                );
        CompletableFuture<List<String>> categoriesFuture = CompletableFuture.supplyAsync(() ->categoryService
                                .getCategoryListByAdPoId(adPo.getId())
                        , asyncTaskExecutor)
                .exceptionally(ex -> {
                    log.error("Failed to get categories for adPo: {}", adPo.getId(), ex);
                    return List.of();
                })
                .thenApply(categories ->
                        categories.stream().map(Category::getName).toList()
                );
        // 等待所有任务完成（非阻塞方式）
        CompletableFuture.allOf(graphUrlsFuture, categoriesFuture).join();

        AdPoVo adPoVo = new AdPoVo();
        adPoVo.setInformation(adPo);
        adPoVo.setImage_src(graphUrlsFuture.join());
        adPoVo.setCategories(categoriesFuture.join());
        return adPoVo;
    }

    @Override
    public void ClickIncrement(Long adPoId) {
        adPoClickCounterService.clickIncrement(adPoId);
    }

    @Override
    public List<AdPo> getAdPoListByIds(List<Long> ids) {
        return adPoMapper.getAdPoListByIds(ids);
    }

    @Override
    public List<AdPo> getAdPoInListByKeyWord(String keyWord,List<AdPo> adPoList) {
        if(adPoList==null||adPoList.isEmpty()){
            return List.of();
        }
        List<AdPo> result = adPoList.stream()
                .filter(adPo ->(adPo.getName()!=null && adPo.getName().contains(keyWord))||
                                (adPo.getContent()!=null && adPo.getContent().contains(keyWord))||
                                (adPo.getPlatform()!=null && adPo.getPlatform().contains(keyWord))||
                                (adPo.getType()!=null && adPo.getType().contains(keyWord))
                        )
                .toList();

        if (!result.isEmpty())return result;

        List<AdPo> resultNew = adPoList.stream()
                .filter(adPo -> {
                    List<Category> categories = categoryService.getCategoryListByAdPoId(adPo.getId());
                    return categories.stream()
                            .anyMatch(category ->
                                    category.getName() != null && category.getName().contains(keyWord));
                })
                .toList();

        if (resultNew.isEmpty())throw new AdWebException(404,"未找到");
        return resultNew;
    }
}
