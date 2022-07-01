package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.mapper.HouseBrokerMapper;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {
    @Autowired
    private HouseBrokerMapper houseBrokerMapper;
 
    
    @Override
    protected BaseMapper<HouseBroker> getEntityMapper() {
        return houseBrokerMapper;
    }

    @Override
    public List<HouseBroker> getListByHouseId(Long id) {
        return houseBrokerMapper.getListByHouseId(id);
    }

    @Override
    public void insert(HouseBroker houseBroker,Admin admin) {
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerMapper.insert(houseBroker);
    }
}
