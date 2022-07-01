package com.atguigu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Dict;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.DictService;
import com.qiniu.util.Json;
import com.sun.tracing.dtrace.Attributes;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {
    
    @Autowired
    private DictMapper dictMapper;
    
    @Autowired
    private JedisPool jedisPool;
    @Override
    protected BaseMapper<Dict> getEntityMapper() {
        return dictMapper;
    }

    @Override
    public List<Map<String, Object>> findZnodes(Long parentid) {
        List<Dict> dictList = findListByParentId(parentid);
        List<Map<String,Object>> zNodes = new ArrayList<>();
        for (Dict dict : dictList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",dict.getId());
            map.put("isParent",dictMapper.findCountById(dict.getId())>0);
            map.put("name",dict.getName());
            zNodes.add(map);
        }
//        List<Map<String,Object>> zNodes = dictList.stream().map(dict->{
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("id",dict.getId());
//            map.put("name",dict.getName());
//            map.put("isParent",dictMapper.findCountById(dict.getId())>0);
//            return map;
//        }).collect(Collectors.toList());
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(long parentId) {
        Jedis resource = jedisPool.getResource();
        String key = "shf:dict:parentId:"+parentId;
        String str = resource.get(key);
        if (str!=null){
//            JSONArray objects = JSON.parseArray(str);
//            List<Dict> dicts = objects.toJavaList(Dict.class);
            List<Dict> dicts = JSON.parseArray(str, Dict.class);
            resource.close();
            return dicts;
        }
        List<Dict> dicts = dictMapper.findListByParentId(parentId);
        String redisValue = JSON.toJSONString(dicts);
        resource.set(key,redisValue);
        resource.close();
        return dicts;
    }

    @Override
    public List<Dict> findListByParentCode(String houseType) {
        Long id = dictMapper.findIdByParentCode(houseType);
        return findListByParentId(id);
    }

    @Override
    public Dict findById(long id) {
        return dictMapper.findById(id);
    }
}
