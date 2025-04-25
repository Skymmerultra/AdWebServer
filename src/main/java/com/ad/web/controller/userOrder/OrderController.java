package com.ad.web.controller.userOrder;

import com.ad.web.common.result.Result;
import com.ad.web.entity.UserOrder;
import com.ad.web.service.UserOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "订单handle")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private UserOrderService userOrderService;

    @Operation(description = "根据订单编号查询订单信息")
    @GetMapping("/{id}")
    public Result<UserOrder> getOrderById(@PathVariable("id")Long orderId){
        UserOrder result = userOrderService.getOrderById(orderId);
        return Result.ok(result);
    }

    @Operation(description = "订单开发票")
    @GetMapping("/invoice/{id}")
    public Result orderInvoice(@PathVariable("id")Long orderId){
        userOrderService.orderInvoice(orderId);
        return Result.ok();
    }
}
