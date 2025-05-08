package com.ad.web.service;

import com.ad.web.entity.AdPo;
import com.ad.web.entity.User;
import com.ad.web.entity.UserOrder;
import com.ad.web.entity.vo.adpo.AdPoVo;
import com.ad.web.entity.vo.user.RechargeVo;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    void register(String username,String password);

    long login(String username,String password);


    void uploadOrUpdateUserAvatar(MultipartFile graph,Long userId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    User getUserById(Long userId);

    void updateUSerInfo(User user);

    List<UserOrder> getOrderByUserId(Long userId);

    Integer getBalanceById(Long userId);

    List<AdPoVo> getViewHistoryById(Long userId);

    List<AdPoVo> getFavoritesById(Long userId);

    void recharge(RechargeVo rechargeVo);

    void viewIncrement(Long userId, Long adPoId);

    void changepassword(String oldPassword,String newPassword, Long userId);

    void favoriteIncrement(Long userId, Long adPoId);

    void favoriteDecrement(Long userId, Long adPoId);
}
