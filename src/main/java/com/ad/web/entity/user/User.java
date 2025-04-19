package com.ad.web.entity.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "用户实体")
public class User {
    private Integer userId;
    private String username;
    private Integer balance;
}
