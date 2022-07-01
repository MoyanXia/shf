package com.atguigu.controller;

import com.atguigu.entity.*;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {

   
    @Reference
    private HouseService houseService;
    
    @Reference
    private CommunityService communityService;
    
    @Reference
    private HouseBrokerService houseBrokerService;
    
    @Reference
    private HouseImageService houseImageService;
    
    @Reference
    private UserFollowService userFollowService;
    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result list(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestBody HouseQueryBo houseQueryBo){
        PageInfo<HouseVo> page = houseService.findByPage(pageNum, pageSize, houseQueryBo);
        return Result.ok(page);
    }
    
    @GetMapping("/auth/info/{houseId}")
    public Result info(@PathVariable Long houseId, HttpSession httpSession){
        House house = houseService.getById(houseId);

        Community community = communityService.getById(house.getCommunityId());

        List<HouseBroker> houseBrokerList = houseBrokerService.getListByHouseId(houseId);

        List<HouseImage> houseImage1List = houseImageService.findListByHouseIdAndType(houseId, 1);

        UserInfo existUser = (UserInfo) httpSession.getAttribute("existUser");
        boolean isFollow = userFollowService.getByUserIdAndHouseId(existUser.getId(),houseId);
        
        Map<String, Object> map = new HashMap<>();
        map.put("house", house);
        map.put("community", community);
        map.put("houseBrokerList", houseBrokerList);
        map.put("houseImage1List", houseImage1List);
        map.put("isFollow", isFollow);
        return Result.ok(map);
    }
}
