package com.ad.web.service;

import com.ad.web.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoryListByAdPoId(Long adPoId);

    List<Long> getCategoriesAdPoIdsKeyToName(String keyWord);
}
