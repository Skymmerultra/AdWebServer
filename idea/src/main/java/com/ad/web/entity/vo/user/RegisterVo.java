package com.ad.web.entity.vo.user;

import lombok.Data;

@Data
public class RegisterVo {
    private String username;
    private String password;
    private String checkPassWord;
}
