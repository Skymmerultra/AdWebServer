package com.ad.web.service.Impl;

import com.ad.web.entity.Graph;
import com.ad.web.entity.vo.graph.GraphVo;
import com.ad.web.mapper.GraphMapper;
import com.ad.web.service.FileService;
import com.ad.web.service.GraphService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class GraphServiceImpl implements GraphService {

    @Autowired
    private GraphMapper graphMapper;

    @Autowired
    private FileService fileService;

    @Override
    public GraphVo getGraphByTypeAndId(Integer itemType,Long itemId){
        Graph graph = graphMapper.getGraphByTypeAndId(itemType,itemId);
        GraphVo graphVo = new GraphVo();
        graphVo.setName(graph.getName());
        graphVo.setUrl(graph.getUrl());
        return graphVo;
    }

    @Override
    public String upload(MultipartFile graph,Integer itemType,Long itemId) throws ServerException,
            InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        String url = fileService.upload(graph);
        Graph graphToInsert = new Graph();
        graphToInsert.setName(graph.getOriginalFilename());
        graphToInsert.setUrl(url);
        graphToInsert.setCreateTime(new Date());
        graphToInsert.setItemType(itemType);
        graphToInsert.setItemId(itemId);
        graphToInsert.setIsDeleted(0);
        graphMapper.insert(graphToInsert);
        return url;
    }
}
