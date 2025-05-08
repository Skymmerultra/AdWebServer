package com.ad.web.controller.user;

import com.ad.web.common.result.Result;
import com.ad.web.entity.AdPo;
import com.ad.web.entity.User;
import com.ad.web.entity.UserOrder;
import com.ad.web.entity.vo.adpo.AdPoVo;
import com.ad.web.entity.vo.user.RechargeVo;
import com.ad.web.service.UserService;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@Tag(name = "用户handle")
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "用户注册")
    @PostMapping("/register")
    public Result register(@RequestParam("username") String username,@RequestParam("password") String password){
        userService.register(username,password);
        return Result.ok();
    }
    @Operation(description = "用户登录")
    @PostMapping("/login")
    public Result<Long> login(@RequestParam("username") String username,@RequestParam("password") String password){
        long result = userService.login(username,password);
        return Result.ok(result);
    }

    @Operation(description = "根据id获取用户信息")
    @GetMapping("{id}")
    public Result<User> getUserById(@PathVariable("id") Long userId){
        User result = userService.getUserById(userId);
        return Result.ok(result);
    }

    @Operation(description = "上传或者更改用户头像")
    @PutMapping("/changeavatar")
    public Result uploadOrUpdateUserAvatar(MultipartFile avatar,@RequestParam("id") Long userId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        userService.uploadOrUpdateUserAvatar(avatar,userId);
        return Result.ok();
    }

    @Operation(description = "修改用户信息")
    @PutMapping("/updateUser")
    public Result updateUserInfo(@RequestBody User user){
        userService.updateUSerInfo(user);
        return Result.ok();
    }

    @Operation(description = "修改用户信息")
    @PutMapping("/changepassword")
    public Result changepassword(@RequestParam("oldpassword")String oldPassword,@RequestParam("newpassword")String newPassword,@RequestParam("id")Long userId){
        userService.changepassword(oldPassword,newPassword,userId);
        return Result.ok();
    }

    @Operation(description = "根据用户id获取订单信息")
    @GetMapping("/order/{id}")
    public Result<List<UserOrder>> getOrderByUserId(@PathVariable("id")Long userId){
        List<UserOrder> result = userService.getOrderByUserId(userId);
        return Result.ok(result);
    }

    @Operation(description = "根据用户id获取用户余额")
    @GetMapping("/balance/{id}")
    public Result<Integer> getBalanceById(@PathVariable("id")Long userId){
        Integer result = userService.getBalanceById(userId);
        return Result.ok(result);
    }
    @Operation(description = "用户充值")
    @PostMapping("/recharge")
    public Result recharge(@RequestBody RechargeVo rechargeVo){
        userService.recharge(rechargeVo);
        return Result.ok();
    }

    @Operation(description = "根据用户id获取用户浏览历史")
    @GetMapping("/viewhistory/{id}")
    public Result<List<AdPoVo>> getViewHistoryById(@PathVariable("id")Long userId){
        List<AdPoVo> result = userService.getViewHistoryById(userId);
        return Result.ok(result);
    }
    @Operation(description = "根据用户id让浏览历史增加")
    @GetMapping("/viewIncrement")
    public Result<List<AdPoVo>> viewIncrement(@RequestParam("userId") Long userId,@RequestParam("adPoId")Long adPoId){
        userService.viewIncrement(userId,adPoId);
        return Result.ok();
    }

    @Operation(description = "根据用户id获取用户收藏")
    @GetMapping("/favorites/{id}")
    public Result<List<AdPoVo>> getFavoritesById(@PathVariable("id")Long userId){
        List<AdPoVo> result = userService.getFavoritesById(userId);
        return Result.ok(result);
    }

    @Operation(description = "用户收藏保存")
    @GetMapping("/favoriteIncrement")
    public Result favoriteIncrement(@RequestParam("userId") Long userId,@RequestParam("adPoId")Long adPoId){
        userService.favoriteIncrement(userId,adPoId);
        return Result.ok();
    }
    @Operation(description = "用户取消收藏")
    @GetMapping("/favoriteDecrement")
    public Result favoriteDecrement(@RequestParam("userId") Long userId,@RequestParam("adPoId")Long adPoId){
        userService.favoriteDecrement(userId,adPoId);
        return Result.ok();
    }
}
