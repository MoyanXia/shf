package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Dict;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.Page;

import java.util.List;

public interface DictMapper extends BaseMapper<Dict> {
    List<Dict> findListByParentId(Long parentid);

    Long findCountById(Long id);

    Long findIdByParentCode(String houseType);

    Dict findById(Long id);
    
    String getNameById(Long id);

    
    
}
