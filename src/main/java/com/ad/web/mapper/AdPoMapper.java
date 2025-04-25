package com.ad.web.mapper;

import com.ad.web.entity.AdPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 朱炫宇
* @description 针对表【ad_po_info(广告位信息表)】的数据库操作Mapper
* @createDate 2025-04-22 21:11:16
* @Entity com.ad.web.entity.AdPo
*/
public interface AdPoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AdPo record);

    int insertSelective(AdPo record);

    AdPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdPo record);

    int updateByPrimaryKey(AdPo record);

    List<AdPo> getAllAdPo();

    List<AdPo> getAdPoOrderByClickNum(@Param("choseNum") int choseNum);
}
