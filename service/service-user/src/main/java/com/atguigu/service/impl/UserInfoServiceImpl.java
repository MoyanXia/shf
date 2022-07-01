package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.UserInfo;
import com.atguigu.mapper.UserInfoMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements com.atguigu.service.UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    protected BaseMapper<UserInfo> getEntityMapper() {
        return userInfoMapper;
    }

    @Override
    public UserInfo getByPhone(String phone) {
        return userInfoMapper.getByPhone(phone);
    }
}
