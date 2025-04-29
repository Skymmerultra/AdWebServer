package com.ad.web.common.Enum;

import lombok.Getter;

@Getter
public enum GraphType {
    USER_GRAPH(1,"用户图片"),
    ADVERTISEMENT_POSITION_GRAPH(2,"广告位图片"),
    ADVERTISEMENT_GRAPH(3,"广告图片");

    private final Integer num;
    private final String message;
    GraphType(Integer num, String message){
        this.num = num;
        this.message = message;
    }
}
