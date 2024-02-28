package com.zyq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyq.reggie.entity.Category;
import com.zyq.reggie.entity.Employee;

public interface CategoryService extends IService<Category> {
    public void remove(String id);
}
