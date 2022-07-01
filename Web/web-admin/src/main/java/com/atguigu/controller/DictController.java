package com.atguigu.controller;

import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController {

    private static final String PAGE_INDEX = "dict/index";

    @Reference
    private DictService dictService;
    
    @RequestMapping
    public String index(){
        return PAGE_INDEX;
    }
    
    @ResponseBody
    @GetMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0")Long id){
        List<Map<String,Object>> zNodes =dictService.findZnodes(id);
        return Result.ok(zNodes);
    }

    @ResponseBody
    @GetMapping("/findDictListByParentId/{id}")
    public Result findDictListByParentId(@PathVariable Long id){
        List<Dict> dicts = dictService.findListByParentId(id);
        return Result.ok(dicts);
    }
}
