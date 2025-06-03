package com.ad.web.service.Impl;

import com.ad.web.entity.Admin;
import com.ad.web.entity.vo.user.LoginVo;
import com.ad.web.exception.AdWebException;
import com.ad.web.mapper.AdminMapper;
import com.ad.web.service.AdminService;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;
    @Override
    public Long login(LoginVo adminLoginVo) {
        Admin admin = adminMapper.selectPasswordByName(adminLoginVo.getName());
        if (admin == null){
            throw new AdWebException(404,"账户不存在");
        }
        if (!Objects.equals(DigestUtils.md5Hex(adminLoginVo.getPassword()),adminLoginVo.getPassword())){
            throw new AdWebException(404,"密码错误");
        }
        return admin.getId();
    }
}
