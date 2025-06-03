package com.ad.web.service.Impl;

import com.ad.web.common.Enum.GraphType;
import com.ad.web.entity.*;
import com.ad.web.entity.vo.adpo.AdPoVo;
import com.ad.web.entity.vo.user.RechargeVo;
import com.ad.web.exception.AdWebException;
import com.ad.web.mapper.*;
import com.ad.web.service.AdPoService;
import com.ad.web.service.GraphService;
import com.ad.web.service.UserService;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private GraphService graphService;

    @Autowired
    private UserViewAdMapper userViewAdMapper;

    @Autowired
    private AdPoService adPoService;

    @Autowired
    private UserFavoritesAdMapper userFavoritesAdMapper;

    @Autowired
    private UserRechargeMapper rechargeMapper;
    @Autowired
    private AdPoMapper adPoMapper;

    @Override
    public void register(String username,String password) {
        User userResult = userMapper.getUserByName(username);
        if (userResult != null){
            throw new AdWebException(404,"账号已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5Hex(password));
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    public long login(String username,String password) {
        User userResult = userMapper.getUserByName(username);
        if (userResult == null){
            throw new AdWebException(404,"账号不存在");
        }
        if (!DigestUtils.md5Hex(password).equals(userResult.getPassword())){
            throw new AdWebException(404,"密码错误");
        }
        return userResult.getId();
    }

    @Override
    public void uploadOrUpdateUserAvatar(MultipartFile graph,Long userId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String url = graphService.upload(graph, GraphType.USER_GRAPH.getNum(), userId);
        User user = new User();
        user.setId(userId);
        user.setUpdateTime(new Date());
        user.setAvatarUrl(url);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updateUserInfo(User user) {
        User userResult = userMapper.getUserByName(user.getUsername());
        if (userResult != null){
            throw new AdWebException(404,"用户名已存在");
        }
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<UserOrder> getOrderByUserId(Long userId) {
        return userOrderMapper.getOrderByUserId(userId);
    }

    @Override
    public Integer getBalanceById(Long userId) {
        return userMapper.getBalanceById(userId);
    }

    @Override
    public List<AdPoVo> getViewHistoryById(Long userId) {
        List<AdPo> adPoList = adPoMapper.getViewHistoryByUserid(userId);
        return adPoService.adPoListToVoList(adPoList);
    }

    @Override
    public List<AdPoVo> getFavoritesById(Long userId) {
//        List<AdPo> adPoVoList = adPoService.getAdPoListByIds(userFavoritesAdMapper.getFavorAdPoIdByUserId(userId));
        List<AdPo> adPoVoList = adPoMapper.getFavoritesByUserid(userId);
        return adPoService.adPoListToVoList(adPoVoList);
    }


    @Override
    public void recharge(RechargeVo rechargeVo) {
        UserRecharge userRecharge = new UserRecharge(null,rechargeVo.getId(),new Date(),rechargeVo.getRechargeNum(),1L,null,null,null);
        rechargeMapper.insert(userRecharge);
        userMapper.recharge(rechargeVo.getId(),rechargeVo.getRechargeNum());
    }

    @Override
    public void viewIncrement(Long userId, Long adPoId) {
        long viewDeletedCount = userViewAdMapper.getViewDeletedAdPoCount(userId,adPoId);
        if (viewDeletedCount == 1L){
            userViewAdMapper.updateViewTimeAndDeleted(userId,adPoId);
            return;
        }
        long viewCount = userViewAdMapper.getViewAdPoCount(userId,adPoId);
        if (viewCount==1L){
            userViewAdMapper.updateViewTime(userId,adPoId);
            return;
        }

        UserViewAd userViewAd = new UserViewAd();
        userViewAd.setUserId(userId);
        userViewAd.setAdId(adPoId);
        userViewAdMapper.insert(userViewAd);
    }
    @Override
    public void viewDecrement(Long userId, Long adPoId) {
        userViewAdMapper.deleteView(userId,adPoId);
    }

    @Override
    public void changepassword(String oldPassword,String newPassword, Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(!DigestUtils.md5Hex(oldPassword).equals(user.getPassword())) {
            throw new AdWebException(404,"原密码错误");
        }
        user.setPassword(DigestUtils.md5Hex(newPassword));
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void favorite(Long userId, Long adPoId) {
        Long favorCount = userFavoritesAdMapper.getFavorAdPoCount(userId,adPoId);
        if (favorCount == 1L)userFavoritesAdMapper.deleteFavorByUserIdAndAdPoId(userId,adPoId);
        else if (favorCount == 0){
            Long favorDeletedCount = userFavoritesAdMapper.getFavorDeletedAdPoCount(userId,adPoId);
            if (favorDeletedCount == 1L){
                userFavoritesAdMapper.updateFavorDelete(userId,adPoId);
            }else {
                UserFavoritesAd userFavoritesAd = new UserFavoritesAd();
                userFavoritesAd.setUserId(userId);
                userFavoritesAd.setAdPoId(adPoId);
                userFavoritesAd.setTime(new Date());
                userFavoritesAdMapper.insert(userFavoritesAd);
            }
        }
    }

    @Override
    public boolean isFavorite(Long userId, Long adPoId) {
        Long favorCount = userFavoritesAdMapper.getFavorAdPoCount(userId,adPoId);
        return favorCount == 1L;
    }

    @Override
    public List<AdPoVo> findFavorites(String keyWord,Long userId) {
        List<AdPo> adPoList = adPoService.getAdPoListByIds(userFavoritesAdMapper.getFavorAdPoIdByUserId(userId));
        List<AdPo> result = adPoService.getAdPoInListByKeyWord(keyWord, adPoList);
        return adPoService.adPoListToVoList(result);
    }

    @Override
    public List<AdPoVo> findHistories(String keyWord, Long userId) {
        List<AdPo> adPoList = adPoService.getAdPoListByIds(userViewAdMapper.getViewAdPoIdByUserId(userId));
        List<AdPo> result = adPoService.getAdPoInListByKeyWord(keyWord, adPoList);
        return adPoService.adPoListToVoList(result);
    }

}
