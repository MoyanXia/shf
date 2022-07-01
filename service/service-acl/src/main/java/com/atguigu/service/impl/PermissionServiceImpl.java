package com.atguigu.service.impl;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseService;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    protected BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> findZNodesByRoleId(Long roleId) {
        List<Permission> allPermissionList =  permissionMapper.findAll();
        
        List<Long> assignPermissionList = rolePermissionMapper.findPermissionListByRoleId(roleId);

        List<Map<String, Object>> zNodes = new ArrayList<>();
        for (Permission permission : allPermissionList) {
            HashMap<String, Object> zNode = new HashMap<>();
            zNode.put("id",permission.getId());
            zNode.put("pId",permission.getParentId());
            zNode.put("name",permission.getName());
            zNode.put("open",true);
            if (assignPermissionList.contains(permission.getId())){
                //包含说明已经有该权限，那么打勾显示以勾选
                zNode.put("checked",true);
            }else{
                zNode.put("checked",false);
            }
            zNodes.add(zNode);
        }
        return zNodes;
    }

    @Override
    public List<Permission> findPermissionListByAdminId(Long adminId) {
        List<Permission> permissionList = permissionMapper.findPermissionListByAdminId(adminId);
        if (null == permissionList || 0 == permissionList.size()) {
            return permissionList;
        }
        //返回父子结构的permissionList
        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<Permission> findAll() {
        List<Permission> permissionList = permissionMapper.findAll();
        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<String> getCodeByAdminId(Long id) {
        return permissionMapper.getCodeByAdminId(id);
    }


}
