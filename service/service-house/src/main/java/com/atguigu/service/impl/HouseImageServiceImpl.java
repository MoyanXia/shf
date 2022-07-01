package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseService;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.HouseImage;
import com.atguigu.mapper.HouseImageMapper;
import com.atguigu.service.HouseImageService;
import com.atguigu.service.HouseService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    private HouseImageMapper houseImageMapper;
    @Override
    protected BaseMapper<HouseImage> getEntityMapper() {
        return houseImageMapper;
    }

    @Override
    public List<HouseImage> findListByHouseIdAndType(Long id, int i) {
        return houseImageMapper.findListByHouseIdAndTyoe(id,i);
    }
}
