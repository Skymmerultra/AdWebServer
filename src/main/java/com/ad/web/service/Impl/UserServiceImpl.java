package com.ad.web.service.Impl;

import com.ad.web.common.result.ResultCodeEnum;
import com.ad.web.entity.User;
import com.ad.web.entity.vo.user.LoginVo;
import com.ad.web.exception.AdWebException;
import com.ad.web.mapper.UserMapper;
import com.ad.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(String username,String password) {
        User userResult = userMapper.getUserByName(username);
        if (userResult != null){
            throw new AdWebException(ResultCodeEnum.USER_ACCOUNT_EXIST_ERROR);
        }
        User user = new User();
        user.setNickname(username);
        user.setPassword(DigestUtils.md5Hex(password));
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    public void login1(LoginVo loginVo) {
        User userResult = userMapper.getUserByName(loginVo.getUsername());
        if (userResult == null){
            throw new AdWebException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST_ERROR);
        }
        if (!DigestUtils.md5Hex(loginVo.getPassword()).equals(userResult.getPassword())){
            throw new AdWebException(ResultCodeEnum.USER_ACCOUNT_PASSWORD_ERROR);
        }
    }
}
