package com.ad.web.controller.adpo;

import com.ad.web.common.result.Result;
import com.ad.web.entity.AdPo;
import com.ad.web.entity.vo.adpo.AdPoVo;
import com.ad.web.service.AdPoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adpo")
@Tag(name = "广告位handle")
public class AdPoController {

    @Autowired
    private AdPoService adPoService;

    @Operation(description = "获取所有广告位信息")
    @GetMapping("getAllAdPo")
    public Result<List<AdPo>> getAllAdPo(){
        List<AdPo> result = adPoService.getAllAdPo();
        return Result.ok(result);
    }
    @Operation(description = "获取点击次数前4个广告及其图片")
    @GetMapping("getSomeAdPo")
    public Result<List<AdPoVo>> getSomeAdPoVoOrderByClickNum(){
        List<AdPoVo> result = adPoService.getSomeAdPoVoOrderByClickNum();
        return Result.ok(result);
    }
}
