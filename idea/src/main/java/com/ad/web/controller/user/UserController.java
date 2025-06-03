package com.ad.web.controller.user;

import com.ad.web.common.result.Result;
import com.ad.web.entity.User;
import com.ad.web.entity.UserOrder;
import com.ad.web.entity.vo.adpo.AdPoVo;
import com.ad.web.entity.vo.user.LoginVo;
import com.ad.web.entity.vo.user.RechargeVo;
import com.ad.web.entity.vo.user.UIdAdIdVo;
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
    public Result register(@RequestBody LoginVo register){
        userService.register(register.getName(),register.getPassword());
        return Result.ok();
    }
    @Operation(description = "用户登录")
    @PostMapping("/login")
    public Result<Long> login(@RequestBody LoginVo login){//@RequestParam("username") String username,@RequestParam("password") String password
        log.info("loginVo :{}",login);
        long result = userService.login(login.getName(),login.getPassword());
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
    @PutMapping("/changeInformation")
    public Result updateUserInfo(@RequestBody User user){
        userService.updateUserInfo(user);
        return Result.ok();
    }

    @Operation(description = "修改用户信息")
    @PutMapping("/changepassword")
    public Result changepassword(@RequestParam("id")Long userId,
                                 @RequestParam("oldpassword")String oldPassword,
                                 @RequestParam("newpassword") String newPassword){

        log.info("旧密码:{}",oldPassword);
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
    @GetMapping("/history/{id}")
    public Result<List<AdPoVo>> getViewHistoryById(@PathVariable("id")Long userId){
        List<AdPoVo> result = userService.getViewHistoryById(userId);
        return Result.ok(result);
    }

    @Operation(description = "根据用户id让浏览历史增加")
    @GetMapping("/viewIncrement")
    public Result viewIncrement(@RequestParam("userId") Long userId,@RequestParam("adPoId")Long adPoId){
        userService.viewIncrement(userId,adPoId);
        return Result.ok();
    }

    @Operation(description = "根据用户id删除对应浏览历史")
    @DeleteMapping("/viewDecrement")
    public Result viewDecrement(@RequestParam("userId") Long userId,@RequestParam("adPoId")Long adPoId){
        userService.viewDecrement(userId,adPoId);
        return Result.ok();
    }
    @Operation(description = "搜索浏览历史")
    @GetMapping("/findHistories")
    public Result<List<AdPoVo>> findHistories(@RequestParam("keyWord") String keyWord,@RequestParam("userId") Long userId){
        List<AdPoVo> result = userService.findHistories(keyWord,userId);
        return Result.ok(result);
    }

    @Operation(description = "根据用户id获取用户收藏")
    @GetMapping("/favorites/{id}")
    public Result<List<AdPoVo>> getFavoritesById(@PathVariable("id")Long userId){
        List<AdPoVo> result = userService.getFavoritesById(userId);
        return Result.ok(result);
    }

    @Operation(description = "验证用户是否收藏该广告位")
    @PostMapping("/isFavorite")
    public Result<Boolean> isFavorite(@RequestBody UIdAdIdVo uIdAdIdVo){
        log.info(uIdAdIdVo.toString());
        boolean result = userService.isFavorite(uIdAdIdVo.getUserId(),uIdAdIdVo.getAdPoId());
        return Result.ok(result);
    }

    @Operation(description = "用户收藏或取消")
    @PostMapping("/favorite")
    public Result favorite(@RequestBody UIdAdIdVo uIdAdIdVo){
        userService.favorite(uIdAdIdVo.getUserId(),uIdAdIdVo.getAdPoId());
        return Result.ok();
    }

    @Operation(description = "搜索收藏的广告")
    @GetMapping("/findFavorites")
    public Result<List<AdPoVo>> findFavorites(@RequestParam("keyWord") String keyWord,@RequestParam("userId") Long userId){
        List<AdPoVo> result = userService.findFavorites(keyWord,userId);
        return Result.ok(result);
    }
}
