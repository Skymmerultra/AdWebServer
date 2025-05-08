package com.ad.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ad.web.mapper")
@SpringBootApplication
public class WebStart {
    public static void main(String[] args) {
        SpringApplication.run(WebStart.class,args);
    }
}
