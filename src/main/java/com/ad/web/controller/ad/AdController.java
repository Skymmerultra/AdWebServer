package com.ad.web.controller.ad;

import com.ad.web.common.result.Result;
import com.ad.web.entity.Ad;
import com.ad.web.entity.Category;
import com.ad.web.service.AdCategoryService;
import com.ad.web.service.AdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
@Tag(name = "广告handle")
public class AdController {
    @Autowired
    private AdService adService;
    @Autowired
    private AdCategoryService adCategoryService;

    @Operation(description = "根据浏览次数排序查询所用广告信息")
    @GetMapping("/getAdAll")
    public Result<List<Ad>> getAdAndGraphList(){
        List<Ad> result = adService.getAdAndGraphList();
        return Result.ok(result);
    }

    @Operation(description = "查询所有广告种类")
    @GetMapping("/getAdCategory")
    public Result<List<Category>> getCategoryListAll(){
        List<Category> result = adCategoryService.getCategoryListAll();
        return Result.ok(result);
    }

    @Operation(description = "根据关键字查询广告")
    @PostMapping("/searchAd")
    public Result<List<Ad>> searchAdList(@RequestParam("searchInfo")String searchInfo){
        List<Ad> result = adService.searchAdList(searchInfo);
        return Result.ok(result);
    }
}
