package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Community;
import com.atguigu.mapper.CommunityMapper;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.CommunityService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private DictMapper dictMapper;
    @Override
    protected BaseMapper<Community> getEntityMapper() {
        return communityMapper;
    }

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum,pageSize);

        Page<Community> page = communityMapper.findPage(filters);
        for (Community community : page) {
            String areaName = communityMapper.getNameById(community.getAreaId());
            community.setAreaName(areaName);
            String plateNmae = communityMapper.getNameById(community.getPlateId());
            community.setPlateName(plateNmae);
        }

        return super.findPage(filters);
    }

    @Override
    public List<Community> findAll() {
        return communityMapper.findAll();
    }

    @Override
    public Community getById(Long id) {
        Community community = communityMapper.getById(id);
        
        community.setAreaName(dictMapper.findById(community.getAreaId()).getName());
        
        community.setPlateName(dictMapper.findById(community.getPlateId()).getName());
        return community;
    }
}
