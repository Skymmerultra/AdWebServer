package com.ad.web.mapper;

import com.ad.web.entity.AdPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 朱炫宇
* @description 针对表【ad_po_info(广告位信息表)】的数据库操作Mapper
* @createDate 2025-04-22 21:11:16
* @Entity com.ad.web.entity.AdPo
*/
public interface AdPoMapper {
    List<AdPo> getAllAdPo();

    List<AdPo> getAdPoOrderByClickNum(@Param("choseNum") int choseNum);

    List<AdPo> getAdPoListById(Long id);

    List<AdPo> getAdPoListKeyToName(String keyWord);

    List<AdPo> getAdPoListKeyToContent(String keyWord);

    List<AdPo> getAdPoListKeyToPlatform(String keyWord);

    List<AdPo> getAdPoListKeyToType(String keyWord);

    int ClickIncrementById(Long adPoId);

    int ClickUpdateById(Long adPoId,Long sum);

    Long getClickNumByAdPoId(Long adPoId);

    @Update("<script>" +
            "UPDATE ad_po_info SET click_num = CASE id " +
            "<foreach collection='list' item='item'>" +
            "WHEN #{item.id} THEN #{item.clickNum} " +
            "</foreach>" +
            "END " +
            "WHERE id IN " +
            "<foreach collection='updates' item='item' open='(' separator=',' close=')'>" +
            "#{item.id}" +
            "</foreach>" +
            "</script>")
    int batchUpdateClickCount(List<AdPo> updates);
    @Select("select id,click_num clickNum from ad_po_info where is_deleted = 0")
    List<AdPo> getAllClickNum();

    List<AdPo> getAdPoListByIds(List<Long> ids);

    AdPo selectByPrimaryKey(Long adPoId);

    List<AdPo> getViewHistoryByUserid(Long userId);

    List<AdPo> getFavoritesByUserid(Long userId);
}
