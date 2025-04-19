package com.ad.web.mapper;

import com.ad.web.entity.AdCategory;

/**
* @author 朱炫宇
* @description 针对表【ad_category(广告类别关联表)】的数据库操作Mapper
* @createDate 2025-04-19 15:19:47
* @Entity com.ad.web.entity.AdCategory
*/
public interface AdCategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AdCategory record);

    int insertSelective(AdCategory record);

    AdCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdCategory record);

    int updateByPrimaryKey(AdCategory record);

}
