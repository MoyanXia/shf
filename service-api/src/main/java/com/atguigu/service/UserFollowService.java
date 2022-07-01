package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;

public interface UserFollowService extends BaseService<UserFollow> {
    void addfollow(Long houseId, Long id);

    void cancelFollow(Long houseId, Long userId);

    boolean getByUserIdAndHouseId(Long id, Long houseId);

    PageInfo<UserFollowVo> getVoById(HashMap<String, Object> filters);
}
