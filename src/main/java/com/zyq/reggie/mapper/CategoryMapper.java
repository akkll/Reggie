package com.zyq.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyq.reggie.entity.Category;
import com.zyq.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
