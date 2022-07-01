package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.Map;

public interface UserFollowMapper extends BaseMapper<UserFollow> {
    void insertByHouseIdAndUserId(@Param("houseId") Long houseId, @Param("userId") Long userId);

    void deleteByUserIdAndHouseId(@Param("userId")Long userId, @Param("houseId")Long houseId);

    UserFollow getByUserIdAndHouseId(@Param("userId")Long userId, @Param("houseId")Long houseId);

    Page<UserFollowVo> getVoById(Map<String, Object> filters);
}
