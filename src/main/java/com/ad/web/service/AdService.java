package com.ad.web.service;

import com.ad.web.entity.Ad;

import java.util.List;

public interface AdService {
    List<Ad> getAdAndGraphList();

    List<Ad> searchAdList(String searchInfo);
}
