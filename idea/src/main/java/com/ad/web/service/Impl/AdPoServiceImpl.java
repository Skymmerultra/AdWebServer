package com.ad.web.service.Impl;

import com.ad.web.common.Enum.GraphType;
import com.ad.web.common.result.ResultCodeEnum;
import com.ad.web.entity.AdPo;
import com.ad.web.entity.Category;
import com.ad.web.entity.vo.graph.GraphVo;
import com.ad.web.exception.AdWebException;
import com.ad.web.mapper.AdPoMapper;
import com.ad.web.entity.vo.adpo.AdPoVo;
import com.ad.web.service.AdPoService;
import com.ad.web.service.CategoryService;
import com.ad.web.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AdPoServiceImpl implements AdPoService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GraphService graphService;
    @Autowired
    private AdPoMapper adPoMapper;

    @Override
    public List<AdPo> getAllAdPo() {
        return adPoMapper.getAllAdPo();
    }

    private static final int choseAdPo = 7;
    @Override
    public List<AdPoVo> getSomeAdPoVoOrderByClickNum() {
        List<AdPo> adPoList = adPoMapper.getAdPoOrderByClickNum(choseAdPo);

        return adPoListToVoList(adPoList);
    }

    @Override
    public List<AdPoVo> adPoListToVoList(List<AdPo> adPoList) {
        return adPoList.stream()
                .map(this::convertToAdPoVo)
                .toList();
    }

    @Override
    public AdPo getAdPoById(Long id) {
        return adPoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AdPoVo> getAdPoListByKeyWord(String keyWord) {
        List<AdPo> adPoListKeyToName = adPoMapper.getAdPoListKeyToName(keyWord);
        if (adPoListKeyToName != null && !adPoListKeyToName.isEmpty()){
            return adPoListToVoList(adPoListKeyToName);
        }
        List<AdPo> adPoListKeyToContent = adPoMapper.getAdPoListKeyToContent(keyWord);
        if (adPoListKeyToContent != null && !adPoListKeyToContent.isEmpty()){
            return adPoListToVoList(adPoListKeyToContent);
        }
        List<AdPo> adPoListKeyToPlatform = adPoMapper.getAdPoListKeyToPlatform(keyWord);
        if (adPoListKeyToPlatform != null && !adPoListKeyToPlatform.isEmpty()){
            return adPoListToVoList(adPoListKeyToPlatform);
        }
        List<AdPo> adPoListKeyToType = adPoMapper.getAdPoListKeyToType(keyWord);
        if (adPoListKeyToType != null && !adPoListKeyToType.isEmpty()){
            return adPoListToVoList(adPoListKeyToType);
        }

        List<Long> categoriesAdPoIdsKeyToName = categoryService.getCategoriesAdPoIdsKeyToName(keyWord);
        List<AdPo> adPoListKeyToCategory = categoriesAdPoIdsKeyToName.stream()
                .map(adPoId->adPoMapper.selectByPrimaryKey(adPoId))
                .toList();
        if (!adPoListKeyToCategory.isEmpty()){
            return adPoListToVoList(adPoListKeyToCategory);
        }
        throw new AdWebException(ResultCodeEnum.KEYWORD_NULL);
    }

    @Override
    public AdPoVo getDetailById(Long adPoId) {
        AdPo adPo = adPoMapper.selectByPrimaryKey(adPoId);
        return convertToAdPoVo(adPo);
    }

    @Override
    public void ClickIncrement(Long adPoId) {
        adPoMapper.ClickIncrementById(adPoId);
    }

    private AdPoVo convertToAdPoVo(AdPo adPo){
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        AdPoVo adPoVo = new AdPoVo();
        adPoVo.setInformation(adPo);

        List<String> graphUrlList = CompletableFuture.supplyAsync(() -> graphService
                        .getGraphListByTypeAndId(GraphType.ADVERTISEMENT_POSITION_GRAPH.getNum(), adPo.getId()), threadPool)
                .join()
                .stream()
                .map(GraphVo::getUrl)
                .toList();

        List<String> categories = CompletableFuture.supplyAsync(() -> categoryService
                        .getCategoryListByAdPoId(adPo.getId()), threadPool)
                .join()
                .stream()
                .map(Category::getName)
                .toList();

        adPoVo.setImage_src(graphUrlList);
        adPoVo.setCategories(categories);
        threadPool.shutdown();
        return adPoVo;
    }
}
