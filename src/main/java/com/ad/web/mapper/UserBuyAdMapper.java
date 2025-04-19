package com.ad.web.mapper;

import com.ad.web.entity.UserBuyAd;

/**
* @author 朱炫宇
* @description 针对表【user_buy_ad(用户购买广告表)】的数据库操作Mapper
* @createDate 2025-04-19 15:19:47
* @Entity com.ad.web.entity.UserBuyAd
*/
public interface UserBuyAdMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserBuyAd record);

    int insertSelective(UserBuyAd record);

    UserBuyAd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBuyAd record);

    int updateByPrimaryKey(UserBuyAd record);

}
