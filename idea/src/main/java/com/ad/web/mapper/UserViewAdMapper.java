package com.ad.web.mapper;

import com.ad.web.entity.UserViewAd;

import java.util.List;

/**
* @author 朱炫宇
* @description 针对表【user_view_ad(用户浏览广告表)】的数据库操作Mapper
* @createDate 2025-04-29 19:34:22
* @Entity com.ad.web.entity.UserViewAd
*/
public interface UserViewAdMapper {

    int insert(UserViewAd record);

    List<Long> getViewAdPoIdByUserId(Long userId);

    int updateViewTime(Long userId, Long adPoId);

    long getViewAdPoCount(Long userId, Long adPoId);

    long getViewDeletedAdPoCount(Long userId, Long adPoId);

    int updateViewTimeAndDeleted(Long userId, Long adPoId);

    int deleteView(Long userId, Long adPoId);
}
