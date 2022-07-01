package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {

    private static final String PAGE_INDEX = "community/index";
    private static final String PAGE_CREATE = "community/create";
    private static final String PAGE_EDIT = "community/edit";
    private static final String LIST_ACTION = "redirect:/community";

    @Reference
    private CommunityService communityService;
    
    @Reference
    private DictService dictService;
    
    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters, Model model){
        if(!filters.containsKey("areaId")){
            filters.put("areaId","");
        }
        if(!filters.containsKey("plateId")){
            filters.put("plateId","");
        }
        model.addAttribute("filters",filters);
        
        List<Dict> areaList =  dictService.findListByParentId(110000L);
        model.addAttribute("areaList",areaList);
        
        PageInfo<Community> communityList = communityService.findPage(filters);
        model.addAttribute("page",communityList);
        
        return PAGE_INDEX;
    }
    
    @RequestMapping("/create")
    public String create(Model model){
        List<Dict> areaList = dictService.findListByParentId(110000L);
        model.addAttribute("areaList",areaList);
        return PAGE_CREATE;
    }
    
    @PostMapping("/save")
    public String save(Community community,Model model){
        communityService.insert(community);
        return successPage(model,"新增小区成功!~");
    }
    
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Community community = communityService.getById(id);
        model.addAttribute("community",community);
        List<Dict> areaList = dictService.findListByParentId(110000L);
        model.addAttribute("areaList",areaList);
        return PAGE_EDIT;
    }
    
    @PostMapping("/update")
    public String update(Community community,Model model){
        communityService.update(community);
        return successPage(model,"修改小区信息成功");
    }
    
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        communityService.delete(id);
        return LIST_ACTION;
    }
    
}
