package com.ad.web.service.Impl;

import com.ad.web.common.Enum.GraphType;
import com.ad.web.common.result.ResultCodeEnum;
import com.ad.web.entity.User;
import com.ad.web.entity.UserOrder;
import com.ad.web.exception.AdWebException;
import com.ad.web.mapper.UserMapper;
import com.ad.web.mapper.UserOrderMapper;
import com.ad.web.service.GraphService;
import com.ad.web.service.UserService;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private GraphService graphService;

    @Override
    public void register(String username,String password) {
        User userResult = userMapper.getUserByName(username);
        if (userResult != null){
            throw new AdWebException(ResultCodeEnum.USER_ACCOUNT_EXIST_ERROR);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5Hex(password));
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    public long login(String username,String password) {
        User userResult = userMapper.getUserByName(username);
        if (userResult == null){
            throw new AdWebException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST_ERROR);
        }
        if (!DigestUtils.md5Hex(password).equals(userResult.getPassword())){
            throw new AdWebException(ResultCodeEnum.USER_ACCOUNT_PASSWORD_ERROR);
        }
        return userResult.getId();
    }

    @Override
    public void uploadOrUpdateUserAvatar(MultipartFile graph,Long userId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String url = graphService.upload(graph, GraphType.USER_GRAPH.getNum(), userId);
        User user = new User();
        user.setId(userId);
        user.setUpdateTime(new Date());
        user.setAvatarUrl(url);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updateUSerInfo(User user) {
        User userResult = userMapper.getUserByName(user.getUsername());
        if (userResult != null){
            throw new AdWebException(ResultCodeEnum.USER_ACCOUNT_EXIST_ERROR);
        }
        if (user.getPassword()!=null && !user.getPassword().isEmpty()){
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        }
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public UserOrder getOrderByUserId(Long userId) {
        return userOrderMapper.getOrderByUserId(userId);
    }

    @Override
    public Integer getBalanceById(Long userId) {
        return userMapper.getBalanceById(userId);
    }
}
