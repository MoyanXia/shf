package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.PageInfo;

public interface HouseService extends BaseService<House> {

    PageInfo<HouseVo> findByPage(Integer pageNum, Integer pageSize, HouseQueryBo houseQueryBo);
}
