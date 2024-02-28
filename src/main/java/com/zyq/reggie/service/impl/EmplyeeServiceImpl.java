package com.zyq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyq.reggie.entity.Employee;
import com.zyq.reggie.mapper.EmployeeMapper;
import com.zyq.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmplyeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
