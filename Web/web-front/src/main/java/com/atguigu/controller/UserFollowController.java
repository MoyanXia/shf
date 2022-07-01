package com.atguigu.controller;

import com.atguigu.entity.UserInfo;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/userFollow")
public class UserFollowController {
    
    @Reference
    private UserFollowService userFollowService;
    
    @GetMapping("/auth/follow/{houseId}")
    public Result addfollow(@PathVariable Long houseId, HttpSession httpSession){
        UserInfo existUser = (UserInfo) httpSession.getAttribute("existUser");
        userFollowService.addfollow(houseId,existUser.getId());
        return Result.ok();
    }

    @GetMapping("/auth/cancelFollow/{houseId}")
    public Result cancelFollow(@PathVariable Long houseId,HttpSession httpSession){
        UserInfo existUser = (UserInfo) httpSession.getAttribute("existUser");
        userFollowService.cancelFollow(houseId,existUser.getId());
        return Result.ok();
    }
    
    @GetMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize,HttpSession httpSession){
        UserInfo existUser = (UserInfo) httpSession.getAttribute("existUser");
        HashMap<String, Object> filters = new HashMap<>();
        filters.put("pageNum",pageNum);
        filters.put("pageSize",pageSize);
        filters.put("userId",existUser.getId());
        PageInfo<UserFollowVo> pageInfo = userFollowService.getVoById(filters);
        
        for (UserFollowVo userFollowVo : pageInfo.getList()) {
            userFollowVo.setCreateTimeString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userFollowVo.getCreateTime()));
        }
        return Result.ok(pageInfo);
    }

    @GetMapping("/auth/cancelFollowById/{id}")
    public Result cancelFollowById(@PathVariable Long id){
        userFollowService.delete(id);
        return Result.ok();
    }
}
