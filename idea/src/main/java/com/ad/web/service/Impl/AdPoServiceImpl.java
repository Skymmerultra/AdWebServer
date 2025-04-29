package com.ad.web.service.Impl;

import com.ad.web.common.Enum.GraphType;
import com.ad.web.entity.AdPo;
import com.ad.web.entity.Category;
import com.ad.web.entity.vo.graph.GraphVo;
import com.ad.web.mapper.AdPoMapper;
import com.ad.web.entity.vo.adpo.AdPoVo;
import com.ad.web.service.AdPoService;
import com.ad.web.service.CategoryService;
import com.ad.web.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class AdPoServiceImpl implements AdPoService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GraphService graphService;
    @Autowired
    private AdPoMapper adPoMapper;

    @Override
    public List<AdPo> getAllAdPo() {
        return adPoMapper.getAllAdPo();
    }

    private static final int choseAdPo = 7;
    @Override
    public List<AdPoVo> getSomeAdPoVoOrderByClickNum() {
        List<AdPo> adPoList = adPoMapper.getAdPoOrderByClickNum(choseAdPo);

        return adPoList.stream()
                .map(this::convertToAdPoVo)
                .toList();
    }

    @Override
    public AdPo getAdPoById(Long id) {
        return adPoMapper.selectByPrimaryKey(id);
    }

    private AdPoVo convertToAdPoVo(AdPo adPo){
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        AdPoVo adPoVo = new AdPoVo();
        adPoVo.setInformation(adPo);

        List<String> graphUrlList = CompletableFuture.supplyAsync(() -> graphService
                        .getGraphListByTypeAndId(GraphType.ADVERTISEMENT_POSITION_GRAPH.getNum(), adPo.getId()), threadPool)
                .join()
                .stream()
                .map(GraphVo::getUrl)
                .toList();

        List<String> categories = CompletableFuture.supplyAsync(() -> categoryService
                        .getCategoryListByAdPoId(adPo.getId()), threadPool)
                .join()
                .stream()
                .map(Category::getName)
                .toList();

        adPoVo.setImage_src(graphUrlList);
        adPoVo.setCategories(categories);
        return adPoVo;
    }
}
