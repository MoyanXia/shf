package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/house")
public class HouseController extends BaseController {

    private static final String PAGE_INDEX = "house/index";
    private static final String PAGE_EDIT = "house/edit";
    private static final String LIST_ACTION = "redirect:/house";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_SHOW = "house/show";
    @Reference
    private HouseService houseService;
    
    @Reference
    private CommunityService communityService;
    
    @Reference
    private DictService dictService;
    
    @Reference
    private HouseBrokerService houseBrokerService;
    
    @Reference
    private HouseUserService houseUserService;
    
    @Reference
    private HouseImageService houseImageService;
    
    @RequestMapping()
    public String index(Model model, @RequestParam Map<String,Object> filters){
        
        model.addAttribute("filters",filters);

        saveAllDictToRequestScope(model);

        PageInfo<House> pageInfo = houseService.findPage(filters);
        model.addAttribute("page",pageInfo);
        return PAGE_INDEX;

    }

    private void saveAllDictToRequestScope(Model model) {
        List<Community> communityList = communityService.findAll();
        model.addAttribute("communityList",communityList);
        //community,
        // 
        // houseType,floor,buildStructure,decoration,direction,houseUse
        List<Dict> houseTypeList = dictService.findListByParentCode("houseType");
        model.addAttribute("houseTypeList",houseTypeList);

        List<Dict> floorList = dictService.findListByParentCode("floor");
        model.addAttribute("floorList",floorList);

        List<Dict> buildStructureList = dictService.findListByParentCode("buildStructure");
        model.addAttribute("buildStructureList",buildStructureList);

        List<Dict> decorationList = dictService.findListByParentCode("decoration");
        model.addAttribute("decorationList",decorationList);

        List<Dict> directionList = dictService.findListByParentCode("direction");
        model.addAttribute("directionList",directionList);

        List<Dict> houseUseList = dictService.findListByParentCode("houseUse");
        model.addAttribute("houseUseList",houseUseList);
    }
    
    @RequestMapping("/create")
    public String create(Model model){
        saveAllDictToRequestScope(model);
        return PAGE_CREATE;
    }
    
    @PostMapping("/save")
    public String save(House house,Model model){
        houseService.insert(house);
        return successPage(model,"添加房源成功");
    }
    
    

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        saveAllDictToRequestScope(model);
        return PAGE_EDIT;
    }
    
    @PostMapping("/update")
    public String update(House house,Model model){
        houseService.update(house);
        return successPage(model,"修改房源信息成功");
    }
    
    @RequestMapping("/delete/{houseId}")
    public String delete(@PathVariable Long houseId){
        houseService.delete(houseId);
        return LIST_ACTION;
    }
    
    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable Long id,@PathVariable Integer status){
        House house = new House();
        house.setStatus(status);
        house.setId(id);
        houseService.update(house);
        return LIST_ACTION;
    }
    
    @RequestMapping("/{id}")
    public String show(@PathVariable Long id,Model model){
        //house信息
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        
        //community信息
        //重写了getById方法，改变原始功能
        Community community = communityService.getById(house.getCommunityId());
        model.addAttribute("community",community);
        //houseBroker信息
        List<HouseBroker> houseBrokerList = houseBrokerService.getListByHouseId(id);
        model.addAttribute("houseBrokerList",houseBrokerList);
        //houseUser信息
        List<HouseUser> houseUserList = houseUserService.getListByHouseId(id);
        model.addAttribute("houseUserList",houseUserList);
        //houseImage1List房源图片
        List<HouseImage> houseImage1List = houseImageService.findListByHouseIdAndType(id,1);
        model.addAttribute("houseImage1List",houseImage1List);
        //houseImage2List房产图片
        List<HouseImage> houseImage2List = houseImageService.findListByHouseIdAndType(id,2);
        model.addAttribute("houseImage2List",houseImage2List);
        return PAGE_SHOW;
    }
    
}
