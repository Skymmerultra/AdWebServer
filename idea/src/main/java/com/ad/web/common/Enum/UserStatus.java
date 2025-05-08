package com.ad.web.common.Enum;

import lombok.Getter;

@Getter
public enum UserStatus {
    ONLINE(1,"用户在线"),
    OFFLINE(0,"用户离线");

    private final Integer num;
    private final String  message;
    UserStatus(Integer num, String message){
        this.num = num;
        this.message = message;
    }
}
