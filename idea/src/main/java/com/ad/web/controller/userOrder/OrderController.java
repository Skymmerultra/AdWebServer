package com.ad.web.controller.userOrder;

import com.ad.web.common.result.Result;
import com.ad.web.entity.UserOrder;
import com.ad.web.service.UserOrderService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;

@Slf4j
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

    @Operation(description = "生成订单")
    @PostMapping
    public Result createOrder(@RequestParam("file") MultipartFile file,
                              @RequestParam("userId")Long userId,
                              @RequestParam("adPoId")Long adPoId,
                              @RequestParam("isInvoice")Integer isInvoice,
                              @RequestParam("payment")Integer payment,
                              @RequestParam("payType")String payType,
                              @RequestParam("deliveryNum")Integer deliveryNum,
                              @RequestParam("startTime")@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                              @RequestParam("endTime")@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime
                              ) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        userOrderService.createOrder(file,userId,adPoId,isInvoice,deliveryNum,startTime,endTime,payment,payType);
        return Result.ok();
    }
}
