package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<HouseBroker> getListByHouseId(Long id);

    void insert(HouseBroker houseBroker, Admin admin);
}
