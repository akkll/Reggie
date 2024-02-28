package com.zyq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyq.reggie.entity.AddressBook;
import com.zyq.reggie.entity.Employee;
import com.zyq.reggie.mapper.AddressBookMapper;
import com.zyq.reggie.mapper.EmployeeMapper;
import com.zyq.reggie.service.AddressBookService;
import com.zyq.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
