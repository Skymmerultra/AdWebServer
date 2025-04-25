package com.ad.web.service;

import com.ad.web.entity.User;
import com.ad.web.entity.UserOrder;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    void register(String username,String password);

    long login(String username,String password);


    void uploadOrUpdateUserAvatar(MultipartFile graph,Long userId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    User getUserById(Long userId);

    void updateUSerInfo(User user);

    UserOrder getOrderByUserId(Long userId);

    Integer getBalanceById(Long userId);
}
