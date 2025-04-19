package com.ad.web.service.Impl;

import com.ad.web.entity.Category;
import com.ad.web.mapper.CategoryMapper;
import com.ad.web.service.AdCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdCategoryServiceImpl implements AdCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryListAll() {
        return categoryMapper.list();
    }
}
