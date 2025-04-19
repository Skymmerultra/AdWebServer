package com.ad.web.service.Impl;

import com.ad.web.entity.Ad;
import com.ad.web.mapper.AdMapper;
import com.ad.web.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdMapper adMapper;

    @Override
    public List<Ad> getAdListAll() {
        return adMapper.list();
    }

    @Override
    public List<Ad> searchAdList(String searchInfo) {
        return adMapper.getAdByInfo(searchInfo);
    }
}
