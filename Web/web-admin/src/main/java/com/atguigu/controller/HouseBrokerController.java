package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {
    private static final String PAGE_CREATE = "houseBroker/create";
    private static final String PAGE_EDIT = "houseBroker/edit";
    private static final String LIST_ACTION = "redirect:/house/";
    @Reference
    private AdminService adminService;
    
    @Reference
    private HouseBrokerService houseBrokerService;
    
    @RequestMapping("/create")
    public String create(HouseBroker houseBroker, Model model){
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList",adminList);
        model.addAttribute("houseBroker",houseBroker);
        return PAGE_CREATE;
    }
    
    
    //houseBroker里面有houseId和brokerId(即为adminId)
    //需要远程调用AdminService，使用的时候调用？
    @PostMapping("/save")
    public String save(Model model,HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBrokerService.insert(houseBroker,admin);
        return successPage(model,"添加经纪人成功");
    }
    
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        HouseBroker houseBroker = houseBrokerService.getById(id);
        model.addAttribute("houseBroker",houseBroker);
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList",adminList);
        return PAGE_EDIT;
    }
    
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,HouseBroker houseBroker,Model model){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setId(id);
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return successPage(model,"修改经纪人成功");
    }
    
    //通过successPage方法为什么不需要houseId
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        houseBrokerService.delete(id);
        return LIST_ACTION + houseId;
    }
    
}
