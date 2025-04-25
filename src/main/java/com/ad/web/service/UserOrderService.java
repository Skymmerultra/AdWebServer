package com.ad.web.service;

import com.ad.web.entity.UserOrder;

public interface UserOrderService {
    UserOrder getOrderById(Long orderId);

    void orderInvoice(Long orderId);
}
