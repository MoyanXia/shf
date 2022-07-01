package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.mapper.UserFollowMapper;
import com.atguigu.service.UserFollowService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@Service
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {
    @Autowired
    private UserFollowMapper userFollowMapper;
    
    @Override
    protected BaseMapper<UserFollow> getEntityMapper() {
        return userFollowMapper;
    }

    @Override
    public void addfollow(Long houseId, Long id) {
        userFollowMapper.insertByHouseIdAndUserId(houseId,id);
    }

    @Override
    public void cancelFollow(Long houseId, Long userId) {
        userFollowMapper.deleteByUserIdAndHouseId(userId,houseId);
    }

    @Override
    public boolean getByUserIdAndHouseId(Long userId, Long houseId) {
        UserFollow userFollow= userFollowMapper.getByUserIdAndHouseId(userId,houseId);
        return userFollow != null;
    }

    @Override
    public PageInfo<UserFollowVo> getVoById(HashMap<String, Object> filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum,pageSize);
        
        
        return new PageInfo<>(userFollowMapper.getVoById(filters));
    }
}
