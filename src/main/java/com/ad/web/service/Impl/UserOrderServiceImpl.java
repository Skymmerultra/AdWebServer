package com.ad.web.service.Impl;

import com.ad.web.entity.UserOrder;
import com.ad.web.mapper.UserOrderMapper;
import com.ad.web.service.FileService;
import com.ad.web.service.UserOrderService;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Slf4j
@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private FileService fileService;
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

    @Override
    public void createOrder(MultipartFile file, Long userId, Long adPoId, Integer isInvoice, Integer deliveryNum, Date startTime, Date endTime) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String content = fileService.upload(file);

        UserOrder order = new UserOrder(null,userId,adPoId,new Date(),isInvoice,deliveryNum,startTime,endTime,content,new Date(),null,0);
        userOrderMapper.insertSelective(order);

    }
}
