package com.atguigu.aspect;


import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;
//
@Aspect
@Component
public class ControllerAspect {
    
    @Reference
    private AdminService adminService;  
    
    Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
    
    @Around("execution(* *..*Controller.*(..))")
    public Object around(ProceedingJoinPoint pjp){

        //用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if (null == userName || "anonymousUser".equals(userName)) {
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        Admin admin = adminService.getByName(userName);
        Long userId = admin.getId();
        
        //request信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = request.getRemoteHost();
        String method = request.getMethod();
        String url = request.getRequestURI();

        //方法签名
        Signature signature = pjp.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        
        //参数
        Object[] args = pjp.getArgs();
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if(i != args.length - 1){
                params.append("param:"+(i+1)+":"+args[i]);
            }else{
                params.append("param:"+(i+1)+":"+args[i]+",");
            }
        }
        long startTime = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            long endTime = new Date().getTime();
        long time = startTime - endTime;
        logger.info("用户编号 : " + userId + " -- 用户名 : " + userName
                + " -- 请求路径 : " + url + " -- 请求方式 : " + method
                + " -- ip地址 : " + ip + " -- 类名 : " 
                + className + " -- 方法名 : " + methodName + " -- 参数 : "
                + (params.toString().equals("") ? "无参数" : params)
                + " -- 运行时长 : " + time + "毫秒");
        }  
        return null;
    }
}
