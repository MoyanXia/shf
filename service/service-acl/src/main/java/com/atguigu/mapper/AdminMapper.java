package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> findAll();

    Admin getByName(@Param("username")String username);
}
