package com.atguigu.controller;


import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private static final String PAGE_CREATE = "admin/create";
    private static final String PAGE_EDIT = "admin/edit";
    private static final String LIST_ACTION = "redirect:/admin";
    private static final String PAGE_UPLOAD = "admin/upload";
    private static final String PAGE_ASSIGN_SHOW = "admin/assignShow";
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final static String PAGE_INDEX = "admin/index"; 
    
    
    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters, Model model){
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        model.addAttribute("page",pageInfo);
        model.addAttribute("filters",filters);
        
        return PAGE_INDEX;
    }
    
    @GetMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }
    
    @RequestMapping("/save")
    public String save(Admin admin,Model model){
        String oldPwd = admin.getPassword();
        String newPwd = passwordEncoder.encode(oldPwd);
        admin.setPassword(newPwd);
        adminService.insert(admin);
        return successPage(model,"新增用户成功");
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        Admin admin = adminService.getById(id);
        model.addAttribute("admin",admin);
        return PAGE_EDIT;
    }
    
    @PostMapping("/update")
    public String update(Admin admin, Model model){
        adminService.update(admin);
        return successPage(model,"修改用户成功");
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        adminService.delete(id);
        return LIST_ACTION;
    }
    
    @RequestMapping("/uploadShow/{adminId}")
    public String uploadShow(@PathVariable Long adminId,Model model){
        model.addAttribute("id",adminId);
        return PAGE_UPLOAD;
    }
    
    @PostMapping("/upload/{adminId}")
    public String upload(@PathVariable Long adminId, Model model, MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + file.getOriginalFilename().split("\\.")[1];
        QiniuUtils.upload2Qiniu(file.getBytes(),fileName);
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setHeadUrl(QiniuUtils.getUrl(fileName));
        adminService.update(admin);
        return successPage(model,"上传用户头像成功");
    }
    
    @RequestMapping("/assignShow/{adminId}")
    public String assignShow(@PathVariable Long adminId,Model model){
        model.addAttribute("adminId", adminId);
        Map<String, List<Role>> map = roleService.findRoleListByAdminId(adminId);
        model.addAllAttributes(map);
        return PAGE_ASSIGN_SHOW;
    }
    
    @PostMapping("/assignRole")
    public String assignRole(@RequestParam("adminId") Long adminId,
                             @RequestParam("roleIds") List<Long> roleIds,
                             Model model){
        roleService.deleteAdminRolesByAdminId(adminId);
        for (Long roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            roleService.saveAdminRole(adminRole);
        }
        return successPage(model,"分配角色成功");
    }
    
}
