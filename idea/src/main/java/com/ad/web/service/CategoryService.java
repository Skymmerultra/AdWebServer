package com.ad.web.service;

import com.ad.web.entity.AdPo;
import com.ad.web.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<Category> getCategoryListByAdPoId(Long adPoId);

    List<Long> getCategoriesAdPoIdsKeyToName(String keyWord);

    Map<Long, List<Category>> batchGetCategoriesByAdPoIds(List<Long> adPoIds);
}
