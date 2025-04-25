package com.ad.web.mapper;

import com.ad.web.entity.UserOrder;

/**
* @author 朱炫宇
* @description 针对表【user_order(用户订单表)】的数据库操作Mapper
* @createDate 2025-04-25 21:17:14
* @Entity com.ad.web.entity.UserOrder
*/
public interface UserOrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserOrder record);

    int insertSelective(UserOrder record);

    UserOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserOrder record);

    int updateByPrimaryKey(UserOrder record);

    UserOrder getOrderByUserId(Long userId);

    void orderInvoice(Long orderId);
}
