package com.ad.web.service.Impl;

import com.ad.web.entity.Category;
import com.ad.web.mapper.AdPoCategoryMapper;
import com.ad.web.mapper.CategoryMapper;
import com.ad.web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
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

}
