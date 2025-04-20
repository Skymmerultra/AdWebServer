package com.ad.web.mapper;

import com.ad.web.entity.Graph;

/**
* @author 朱炫宇
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2025-04-20 15:25:34
* @Entity com.ad.web.entity.Graph
*/
public interface GraphMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Graph record);

    int insertSelective(Graph record);

    Graph selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Graph record);

    int updateByPrimaryKey(Graph record);

    Graph getGraphByTypeAndId(Integer itemType, Long itemId);
}
