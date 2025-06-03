package com.ad.web.mapper;

import com.ad.web.entity.Admin;

/**
* @author 朱炫宇
* @description 针对表【admin_info(管理员信息表)】的数据库操作Mapper
* @createDate 2025-06-03 19:04:32
* @Entity com.ad.web.entity.Admin
*/
public interface AdminMapper {

    Admin selectPasswordByName(String adminName);
}




