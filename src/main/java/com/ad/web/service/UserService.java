package com.ad.web.service;

import com.ad.web.entity.vo.LoginVo;

public interface UserService {
    void register(String username,String password);

    void login(String username, String password);
}
