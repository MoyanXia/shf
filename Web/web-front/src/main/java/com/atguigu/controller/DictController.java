package com.atguigu.controller;

import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/dict")
public class DictController {
    
    @Reference
    private DictService dictService;
    @GetMapping("/findDictListByParentDictCode/{code}")
    public Result findDictListByParentDictCode(@PathVariable String code){
        List<Dict> dictList = dictService.findListByParentCode(code);
        return Result.ok(dictList);
    }
    
    @GetMapping("/findDictListByParentId/{areaId}")
    public Result findDictListByParentId(@PathVariable Integer areaId){
        List<Dict> dictList = dictService.findListByParentId(areaId);
        return Result.ok(dictList);
    }
}


