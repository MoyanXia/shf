package com.atguigu.controller;


import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FrameController {

    @Reference
    private AdminService adminService;
    
    @Reference
    private PermissionService permissionService;
    

    private static final String PAGE_INDEX = "frame/index";
    private static final String PAGE_MAIN = "frame/main";

    @GetMapping
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Admin admin = adminService.getByName(user.getUsername());
        List<Permission> permissionList = permissionService.findPermissionListByAdminId(admin.getId());
        model.addAttribute("admin",admin);
        model.addAttribute("permissionList",permissionList);
        return PAGE_INDEX;
    }
    @GetMapping("/main")
    public String main(){
        return PAGE_MAIN;
    }
}
