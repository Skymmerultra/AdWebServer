package com.ad.web.mapper;

import com.ad.web.entity.UserFavoritesAd;

import java.util.Collection;
import java.util.List;

/**
* @author 朱炫宇
* @description 针对表【user_favorites_ad(用户收藏广告表)】的数据库操作Mapper
* @createDate 2025-04-29 21:08:22
* @Entity com.ad.web.entity.UserFavoritesAd
*/
public interface UserFavoritesAdMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserFavoritesAd record);

    int insertSelective(UserFavoritesAd record);

    UserFavoritesAd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFavoritesAd record);

    int updateByPrimaryKey(UserFavoritesAd record);

    List<Long> getFavorAdPoIdByUserId(Long userId);

    int deleteFavorByUserIdAndAdPoId(Long userId, Long adPoId);
}
