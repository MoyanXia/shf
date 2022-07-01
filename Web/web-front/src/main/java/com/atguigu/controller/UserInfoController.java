package com.atguigu.controller;

import com.atguigu.entity.UserInfo;
import com.atguigu.entity.bo.LoginBo;
import com.atguigu.entity.bo.RegisterBo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.AdminService;
import com.atguigu.service.UserInfoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    
    @Reference
    private UserInfoService userInfoService;
    @ResponseBody
    @GetMapping("/sendCode/{phoneNum}")
    public Result sendCode(@PathVariable Long phoneNum,HttpSession httpSession){
        String code = "8888";
        httpSession.setAttribute("existCode",code);
        return Result.ok();
    }
    
    @ResponseBody
    @PostMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo, HttpSession httpSession){
        String code = registerBo.getCode();
        String existCode = (String) httpSession.getAttribute("existCode");
        if(!code.equals(existCode)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }

        UserInfo dbUserInfo = userInfoService.getByPhone(registerBo.getPhone());
        if(null != dbUserInfo){
            //用户已存在
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(registerBo.getPhone());
        userInfo.setNickName(registerBo.getNickName());
        userInfo.setPassword(registerBo.getPassword());
        userInfoService.insert(userInfo);
        return Result.ok();
    }
    
    @ResponseBody
    @PostMapping("/login")
    public Result login(@RequestBody LoginBo loginBo,HttpSession httpSession){
        UserInfo dbUserInfo = userInfoService.getByPhone(loginBo.getPhone());
        if (null == dbUserInfo) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        if (!loginBo.getPassword().equals(dbUserInfo.getPassword())) {
            return Result.build(null , ResultCodeEnum.PASSWORD_ERROR);
        }
        if (0 == dbUserInfo.getStatus()) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        httpSession.setAttribute("existUser",dbUserInfo);
        return Result.ok(dbUserInfo);
    }
}
