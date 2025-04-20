package com.ad.web.controller.graph;

import com.ad.web.common.result.Result;
import com.ad.web.entity.vo.graph.GraphVo;
import com.ad.web.service.GraphService;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    private GraphService graphService;

    @Operation(description = "根据类别和Id查询图片")
    @GetMapping("/{itemType}/{itemId}")
    public Result<GraphVo> getGraphByTypeAndId(@PathVariable("itemType")Integer itemType,@PathVariable("itemId")Long itemId) {
        GraphVo result = graphService.getGraphByTypeAndId(itemType, itemId);
        return Result.ok(result);
    }
    @Operation(description = "根据类型和上传图片")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("graph") MultipartFile graph,@RequestParam("itemType") Integer itemType,
                                 @RequestParam("itemId")Long itemId) throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {
        String url = graphService.upload(graph,itemType,itemId);
        return Result.ok(url);
    }
}
