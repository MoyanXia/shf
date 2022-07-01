package com.atguigu.controller;


import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.entity.RolePermission;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.qiniu.util.Json;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
    private static final String PAGE_INDEX = "role/index";
    private static final String PAGE_CREATE = "role/create";
    private static final String LIST_ACTION = "redirect:/role";
    private static final String PAGE_EDIT = "role/edit";
    private static final String PAGE_SUCCESS = "common/successPage";
    private static final String PAGE_ASSIGN_SHOW = "role/assignShow";
    @Reference
    private RoleService roleService;
    
    @Reference
    private PermissionService permissionService;
    
    @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping
    public String index(@RequestParam Map filters,Model model){
//        if (!filters.containsKey("pageNum"))
//            filters.put("pageNum",1);
//        if(!filters.containsKey("pageSize"))
//            filters.put("pageSize",10);
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        model.addAttribute("page",pageInfo);
        model.addAttribute("filters",filters);
        return PAGE_INDEX;
    }

    @PreAuthorize("hasAnyAuthority('role.create')")
    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }
    
    @PreAuthorize("hasAnyAuthority('role.create')")
    @PostMapping("/save")
    public String save(Role role,Model model){
        roleService.insert(role);
        return successPage(model,"新增角色成功");
    }

    @PreAuthorize("hasAnyAuthority('role.edit')")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return PAGE_EDIT;
    }

    @PreAuthorize("hasAnyAuthority('role.edit')")
    @PostMapping("/update")
    public String update(Role role,Model model){
        roleService.update(role);
        return successPage(model,"更新角色成功");
    }

    @PreAuthorize("hasAnyAuthority('role.delete')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        roleService.delete(id);
        return LIST_ACTION;
    }

    @PreAuthorize("hasAnyAuthority('role.assign')")
    @GetMapping("/assignShow/{roleId}")
    public String assignShow(@PathVariable Long roleId,Model model){
        model.addAttribute("roleId",roleId);
        List<Map<String,Object>> zNodes = permissionService.findZNodesByRoleId(roleId);
        String s = JSON.toJSONString(zNodes);
        model.addAttribute("zNodes",s);
        return PAGE_ASSIGN_SHOW;
    }

    @PreAuthorize("hasAnyAuthority('role.assign')")
    @PostMapping("/assignPermission")//permissionIds不能用list接受，用list需要使用@RequestParam
    public String assignPermission(Long roleId,@RequestParam List<Long> permissionIds,Model model){
        roleService.deleteRolePermission(roleId);
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            roleService.saveRolePermission(rolePermission);
        }
        return successPage(model,"分配权限成功");
    }
}
