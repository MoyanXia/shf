package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.RolePermission;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<Long> findPermissionListByRoleId(Long roleId);

    void deleteRolePermission(Long roleId);

    void saveRolePermission(RolePermission rolePermission);
}
