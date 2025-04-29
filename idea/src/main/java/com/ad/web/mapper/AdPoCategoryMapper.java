package com.ad.web.mapper;

import com.ad.web.entity.AdPoCategory;

import java.util.List;

/**
* @author 朱炫宇
* @description 针对表【ad_po_category(广告位类别关联表)】的数据库操作Mapper
* @createDate 2025-04-22 21:29:13
* @Entity com.ad.web.entity.AdPoCategory
*/
public interface AdPoCategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AdPoCategory record);

    int insertSelective(AdPoCategory record);

    AdPoCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdPoCategory record);

    int updateByPrimaryKey(AdPoCategory record);

    List<Long> getCategoryIdsByAdPoId(Long adPoId);
}
