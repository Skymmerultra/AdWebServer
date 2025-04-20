package com.ad.web.entity.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户登录提交信息实体")
public class LoginVo {

    @Schema(description = "username")
    private String username;
    @Schema(description = "password")
    private String password;
}
