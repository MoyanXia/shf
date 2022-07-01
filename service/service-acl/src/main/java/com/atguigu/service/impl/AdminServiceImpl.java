package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Admin;
import com.atguigu.mapper.AdminMapper;
import com.atguigu.service.AdminService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    @Override
    protected BaseMapper<Admin> getEntityMapper() {
        return adminMapper;
    }

    @Override
    public List<Admin> findAll() {
        return adminMapper.findAll();
    }

    @Override
    public Admin getByName(String username) {
        return adminMapper.getByName(username);
    }


}
