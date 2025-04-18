package com.ad.web.controller;

import com.ad.web.entity.User;
import com.ad.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id")Integer userId, Model model){
        User u = userMapper.getUserById(userId);
        log.info(String.valueOf(u));
        model.addAttribute("user",u);
        return "userInfo";
    }
}
