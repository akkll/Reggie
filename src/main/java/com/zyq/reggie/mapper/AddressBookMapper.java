package com.zyq.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyq.reggie.entity.AddressBook;
import com.zyq.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
