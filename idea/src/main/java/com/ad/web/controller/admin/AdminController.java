package com.ad.web.controller.admin;

import com.ad.web.common.result.Result;
import com.ad.web.entity.Admin;
import com.ad.web.entity.vo.user.LoginVo;
import com.ad.web.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理员handle")
public class AdminController {

    @Resource
    private AdminService adminService;
    @PostMapping("/login")
    public Result<Long> login(@RequestBody LoginVo adminLoginVo){
        Long result = adminService.login(adminLoginVo);
        return Result.ok(result);
    }
}
