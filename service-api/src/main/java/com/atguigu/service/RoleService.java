package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.entity.RolePermission;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {

    List<Role> findAll();

    Map<String, List<Role>> findRoleListByAdminId(Long adminId);

    void deleteAdminRolesByAdminId(Long adminId);

    void saveAdminRole(AdminRole adminRole);

    void deleteRolePermission(Long roleId);

    void saveRolePermission(RolePermission rolePermission);
}
