package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseImage;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage>{
    List<HouseImage> findListByHouseIdAndType(Long id, int i);
}
