package com.ad.web.mapper;

import com.ad.web.entity.User;

/**
* @author 朱炫宇
* @description 针对表【user_info(用户信息表)】的数据库操作Mapper
* @createDate 2025-04-25 21:11:00
* @Entity com.ad.web.entity.User
*/
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getUserByName(String username);

    Integer getBalanceById(Long userId);
}
