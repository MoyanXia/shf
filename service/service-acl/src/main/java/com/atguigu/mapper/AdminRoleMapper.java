package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.AdminRole;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    void saveAdminRole(AdminRole adminRole);

    void deleteAdminRolesByAdminId(Long adminId);

    List<Long> findRoleIdListByAdminId(Long adminId);
}
