package com.ad.web.mapper;

import com.ad.web.entity.PaymentType;

/**
* @author 朱炫宇
* @description 针对表【payment_type(用户充值表)】的数据库操作Mapper
* @createDate 2025-04-19 15:19:47
* @Entity com.ad.web.entity.PaymentType
*/
public interface PaymentTypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PaymentType record);

    int insertSelective(PaymentType record);

    PaymentType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentType record);

    int updateByPrimaryKey(PaymentType record);

}
