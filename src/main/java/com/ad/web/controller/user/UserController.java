package com.ad.web.controller.user;

import com.ad.web.entity.user.User;
import com.ad.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/user")
@Tag(name = "用户信息管理")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public String Login(User user, Model model){
        model.addAttribute("user",user);
        return "loginSuccess";
    }
}
