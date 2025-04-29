package com.ad.web.entity.vo.adpo;

import com.ad.web.entity.AdPo;
import lombok.Data;

import java.util.List;

@Data
public class AdPoVo {
    private AdPo information;
    private List<String> image_src;
    private List<String> categories;
}
