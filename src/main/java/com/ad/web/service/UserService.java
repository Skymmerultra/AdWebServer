package com.ad.web.service;

import com.ad.web.entity.vo.user.LoginVo;

public interface UserService {
    void register(String username,String password);

    void login1(LoginVo loginVo);
}
