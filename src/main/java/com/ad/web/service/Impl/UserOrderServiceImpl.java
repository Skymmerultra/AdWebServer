package com.ad.web.service.Impl;

import com.ad.web.entity.UserOrder;
import com.ad.web.mapper.UserOrderMapper;
import com.ad.web.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Override
    public UserOrder getOrderById(Long orderId) {
        return userOrderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public void orderInvoice(Long orderId) {
        userOrderMapper.orderInvoice(orderId);
    }
}
