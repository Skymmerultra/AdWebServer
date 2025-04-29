package com.ad.web.service;

import com.ad.web.entity.UserOrder;
import io.minio.errors.*;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public interface UserOrderService {
    UserOrder getOrderById(Long orderId);

    void orderInvoice(Long orderId);

    void createOrder(MultipartFile file, Long userId, Long adPoId, Integer isInvoice, Integer deliveryNum, Date startTime, Date endTime, Integer payment,String payType) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
