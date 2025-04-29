package com.ad.web.mapper;

import com.ad.web.entity.UserOrder;

import java.util.List;

/**
* @author 朱炫宇
* @description 针对表【user_order(用户订单表)】的数据库操作Mapper
* @createDate 2025-04-26 13:21:56
* @Entity com.ad.web.entity.UserOrder
*/
public interface UserOrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserOrder record);

    int insertSelective(UserOrder record);

    UserOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserOrder record);

    int updateByPrimaryKey(UserOrder record);

    int orderInvoice(Long orderId);

    List<UserOrder> getOrderByUserId(Long userId);
}
