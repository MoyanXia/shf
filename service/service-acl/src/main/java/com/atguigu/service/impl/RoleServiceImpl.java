package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.entity.RolePermission;
import com.atguigu.mapper.AdminRoleMapper;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.RoleService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseMapper<Role> getEntityMapper() {
        return roleMapper;
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public Map<String, List<Role>> findRoleListByAdminId(Long adminId) {
        //获取全部Role信息
        List<Role> allRoleList = roleMapper.findAll();
        List<Long> assignRoleIdList = adminRoleMapper.findRoleIdListByAdminId(adminId);
        List<Role> assignRoleList = new ArrayList<>();
        List<Role> unassignRoleList = new ArrayList<>();
        for (Role role : allRoleList) {
            if (assignRoleIdList.contains(role.getId())){
                assignRoleList.add(role);
            }else{
                unassignRoleList.add(role);
            }
        }
        HashMap<String, List<Role>> map = new HashMap<>();
        map.put("assignRoleList",assignRoleList);
        map.put("unAssignRoleList",unassignRoleList);
        return map;
    }

    @Override
    public void deleteAdminRolesByAdminId(Long adminId) {
        adminRoleMapper.deleteAdminRolesByAdminId(adminId);
    }

    @Override
    public void saveAdminRole(AdminRole adminRole) {
        adminRoleMapper.saveAdminRole(adminRole);
    }

    @Override
    public void deleteRolePermission(Long roleId) {
        rolePermissionMapper.deleteRolePermission(roleId);
    }

    @Override
    public void saveRolePermission(RolePermission rolePermission) {
        rolePermissionMapper.saveRolePermission(rolePermission);
    }


}
