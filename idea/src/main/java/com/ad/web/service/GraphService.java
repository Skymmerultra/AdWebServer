package com.ad.web.service;

import com.ad.web.common.Enum.GraphType;
import com.ad.web.entity.User;
import com.ad.web.entity.vo.graph.GraphVo;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface GraphService {
    List<GraphVo> getGraphListByTypeAndId(Integer itemType, Long itemId);

    String upload(MultipartFile graph,Integer itemType,Long itemId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
