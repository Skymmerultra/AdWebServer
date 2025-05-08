package com.ad.web.service.Impl;

import com.ad.web.common.Enum.GraphType;
import com.ad.web.entity.Graph;
import com.ad.web.entity.vo.graph.GraphVo;
import com.ad.web.mapper.GraphMapper;
import com.ad.web.service.FileService;
import com.ad.web.service.GraphService;
import io.minio.errors.*;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class GraphServiceImpl implements GraphService {

    @Autowired
    private GraphMapper graphMapper;

    @Autowired
    private FileService fileService;

    @Override
    public List<GraphVo> getGraphListByTypeAndId(Integer itemType, Long itemId){
        List<Graph> graphList = graphMapper.getGraphListByTypeAndId(itemType,itemId);
        ArrayList<GraphVo> graphVoList = new ArrayList<>();
        for (Graph graph : graphList) {
            GraphVo graphVo = new GraphVo();
            graphVo.setName(graph.getName());
            graphVo.setUrl(graph.getUrl());
            graphVoList.add(graphVo);
        }
        return graphVoList;
    }

    @Override
    public String upload(MultipartFile graph,Integer itemType,Long itemId) throws ServerException,
            InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        String url = fileService.upload(graph);
        if (Objects.equals(itemType, GraphType.USER_GRAPH.getNum())){
            List<Graph> result = graphMapper.getGraphListByTypeAndId(itemType, itemId);
            if (!result.isEmpty()){
                Graph graph1 = result.get(0);
                graph1.setUrl(url);
                graph1.setUpdateTime(new Date());
                graphMapper.updateByPrimaryKey(graph1);
            }else {
                insertGraph(graph, itemType, itemId, url);
            }
        }else {
            insertGraph(graph, itemType, itemId, url);
        }
        return url;
    }

    private void insertGraph(MultipartFile graph, Integer itemType, Long itemId, String url) {
        Graph graphToInsert = new Graph();
        graphToInsert.setName(graph.getOriginalFilename());
        graphToInsert.setUrl(url);
        graphToInsert.setCreateTime(new Date());
        graphToInsert.setItemType(itemType);
        graphToInsert.setItemId(itemId);
        graphToInsert.setIsDeleted(0);
        graphMapper.insert(graphToInsert);
    }

}
