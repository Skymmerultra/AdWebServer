package com.ad.web.service;

import com.ad.web.entity.AdPo;
import com.ad.web.entity.vo.adpo.AdPoVo;

import java.util.List;

public interface AdPoService {
    List<AdPo> getAllAdPo();

    List<AdPoVo> getSomeAdPoVoOrderByClickNum();

    List<AdPoVo> adPoListToVoList(List<AdPo> adPoList);

    AdPo getAdPoById(Long id);

    List<AdPoVo> getAdPoListByKeyWord(String keyWord);

    AdPoVo getDetailById(Long adPoId);

    void ClickIncrement(Long adPoId);
}
