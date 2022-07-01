package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Community;

import java.util.List;

public interface CommunityMapper extends BaseMapper<Community> {
    String getNameById(Long areaId);

    List<Community> findAll();
    
}
