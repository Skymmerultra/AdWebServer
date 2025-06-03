package com.ad.web.service;

import com.ad.web.entity.vo.user.LoginVo;

public interface AdminService {
    Long login(LoginVo adminLoginVo);
}
