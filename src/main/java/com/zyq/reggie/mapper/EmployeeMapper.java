package com.zyq.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyq.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
@Mapper
@Component
public interface EmployeeMapper extends BaseMapper<Employee> {
}
