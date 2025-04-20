package com.ad.web.controller.user;

import com.ad.web.common.result.Result;
import com.ad.web.entity.vo.user.LoginVo;
import com.ad.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "用户handle")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "用户注册")
    @PostMapping("/register")
    public Result register(String username,String password){
        userService.register(username,password);
        return Result.ok();
    }

    @Operation(description = "用户登录")
    @PostMapping("/login1")
    public Result login1(LoginVo loginVo){
        userService.login1(loginVo);
        return Result.ok();
    }
}
