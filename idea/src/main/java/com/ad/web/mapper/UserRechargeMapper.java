package com.ad.web.mapper;

import com.ad.web.entity.UserRecharge;

/**
* @author 朱炫宇
* @description 针对表【user_recharge(用户充值表)】的数据库操作Mapper
* @createDate 2025-04-30 21:24:14
* @Entity com.ad.web.entity.UserRecharge
*/
public interface UserRechargeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRecharge record);

    int insertSelective(UserRecharge record);

    UserRecharge selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRecharge record);

    int updateByPrimaryKey(UserRecharge record);

}
