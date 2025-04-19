package com.ad.web.mapper;

import com.ad.web.entity.UserViewAd;

/**
* @author 朱炫宇
* @description 针对表【user_view_ad(用户浏览广告表)】的数据库操作Mapper
* @createDate 2025-04-19 15:19:47
* @Entity com.ad.web.entity.UserViewAd
*/
public interface UserViewAdMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserViewAd record);

    int insertSelective(UserViewAd record);

    UserViewAd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserViewAd record);

    int updateByPrimaryKey(UserViewAd record);

}
