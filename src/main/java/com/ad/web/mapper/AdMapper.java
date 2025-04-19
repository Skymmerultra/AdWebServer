package com.ad.web.mapper;

import com.ad.web.entity.Ad;

import java.util.List;

/**
* @author 朱炫宇
* @description 针对表【ad_info(广告信息表)】的数据库操作Mapper
* @createDate 2025-04-19 15:29:16
* @Entity com.ad.web.entity.Ad
*/
public interface AdMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Ad record);

    int insertSelective(Ad record);

    Ad selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Ad record);

    int updateByPrimaryKey(Ad record);

    List<Ad> list();

    List<Ad> getAdByInfo(String searchInfo);
}
