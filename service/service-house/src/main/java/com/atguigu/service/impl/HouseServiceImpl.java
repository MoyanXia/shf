package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.mapper.DictMapper;
import com.atguigu.mapper.HouseMapper;
import com.atguigu.service.HouseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tracing.dtrace.Attributes;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private DictMapper dictMapper;
    
    @Override
    protected BaseMapper<House> getEntityMapper() {
        return houseMapper;
    }

    @Override
    public House getById(Long id) {
        House house = houseMapper.getById(id);
        house.setHouseTypeName(dictMapper.getNameById(house.getHouseTypeId()));
        house.setFloorName(dictMapper.getNameById(house.getFloorId()));
        house.setBuildStructureName(dictMapper.getNameById(house.getBuildStructureId()));
        house.setDirectionName(dictMapper.getNameById(house.getDirectionId()));
        house.setDecorationName(dictMapper.getNameById(house.getDecorationId()));
        house.setHouseUseName(dictMapper.getNameById(house.getHouseUseId()));
        return house;
    }


    @Override
    public PageInfo<HouseVo> findByPage(Integer pageNum, Integer pageSize, HouseQueryBo houseQueryBo) {
        PageHelper.startPage(pageNum,pageSize);
        Page<HouseVo> page = houseMapper.findByPage(houseQueryBo);
        return new PageInfo<>(page);
    }
}
