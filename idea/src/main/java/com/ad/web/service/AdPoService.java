package com.ad.web.service;

import com.ad.web.entity.AdPo;
import com.ad.web.entity.vo.adpo.AdPoVo;

import java.util.List;

public interface AdPoService {
    List<AdPo> getAllAdPo();

    List<AdPoVo> getSomeAdPoVoOrderByClickNum();

    AdPo getAdPoById(Long id);
}
