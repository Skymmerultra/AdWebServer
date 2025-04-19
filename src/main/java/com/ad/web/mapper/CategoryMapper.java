package com.ad.web.mapper;

import com.ad.web.entity.Category;

import java.util.List;

/**
* @author 朱炫宇
* @description 针对表【category(广告种类表)】的数据库操作Mapper
* @createDate 2025-04-19 15:19:47
* @Entity com.ad.web.entity.Category
*/
public interface CategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> list();
}
