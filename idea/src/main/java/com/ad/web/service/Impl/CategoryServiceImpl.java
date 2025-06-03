package com.ad.web.service.Impl;

import com.ad.web.entity.AdPo;
import com.ad.web.entity.Category;
import com.ad.web.entity.vo.category.CategoryRelation;
import com.ad.web.mapper.AdPoCategoryMapper;
import com.ad.web.mapper.CategoryMapper;
import com.ad.web.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private AdPoCategoryMapper adPoCategoryMapper;

    @Override
    public List<Category> getCategoryListByAdPoId(Long adPoId){
        List<Long> categoryIds = adPoCategoryMapper.getCategoryIdsByAdPoId(adPoId);
        return categoryIds.stream()
                .map(categoryId -> categoryMapper.selectByPrimaryKey(categoryId))
                .toList();
    }

    @Override
    public List<Long> getCategoriesAdPoIdsKeyToName(String keyWord) {
        List<Long> categoriesIdKeyToName = categoryMapper.getCategoriesIdKeyToName(keyWord);
        return categoriesIdKeyToName.stream()
                .map(categoryId -> {
                    return adPoCategoryMapper.getAdPoIdsByCategoryId(categoryId);
                })
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public Map<Long, List<Category>> batchGetCategoriesByAdPoIds(List<Long> adPoIds) {
        if (adPoIds==null || adPoIds.isEmpty())return Map.of();

        try {
            // 去重处理
            List<Long> distinctIds = adPoIds.stream().distinct().toList();

            // 批量查询
            List<CategoryRelation> relations = categoryMapper.batchSelectByAdPoIds(distinctIds);

            // 按广告位ID分组
            return relations.stream()
                    .collect(Collectors.groupingBy(
                            CategoryRelation::getAdPoId,
                            Collectors.mapping(CategoryRelation::getCategory,Collectors.toList())
                    ));

        } catch (Exception e) {
            log.error("批量获取广告位分类失败，adPoIds: {}", adPoIds, e);
            return Map.of();
        }
    }

}
