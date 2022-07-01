package com.atguigu.config;

import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import javafx.beans.property.SimpleListProperty;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getByName(username);
        if (admin==null){
            throw new UsernameNotFoundException("不存在的");
            
//            return null;
        }
        List<String> codeList = permissionService.getCodeByAdminId(admin.getId());
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (String s : codeList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(s);
            authorities.add(simpleGrantedAuthority);
        }
        
        return new User(username,admin.getPassword(),authorities);
    }
}