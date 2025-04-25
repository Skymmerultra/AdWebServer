package com.ad.web.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AdWeb项目API")
                        .version("1.0")
                        .description("AdWeb项目的接口文档"));
    }

    @Bean
    public GroupedOpenApi userAPI() {
        return GroupedOpenApi.builder().group("用户信息管理").
                pathsToMatch("/user/**").
                build();
    }
    @Bean
    public GroupedOpenApi graphAPI() {
        return GroupedOpenApi.builder().group("图片信息管理").
                pathsToMatch("/graph/**").
                build();
    }
    @Bean
    public GroupedOpenApi adpoAPI() {
        return GroupedOpenApi.builder().group("广告位信息管理").
                pathsToMatch("/adpo/**").
                build();
    }
    @Bean
    public GroupedOpenApi orderAPI() {
        return GroupedOpenApi.builder().group("订单信息管理").
                pathsToMatch("/order/**").
                build();
    }
}
