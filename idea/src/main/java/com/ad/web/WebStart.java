package com.ad.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@MapperScan("com.ad.web.mapper")
@SpringBootApplication
@EnableScheduling
public class WebStart {
    public static void main(String[] args) {
        SpringApplication.run(WebStart.class,args);
    }
}
