package com.zyq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyq.reggie.entity.Dish;
import com.zyq.reggie.entity.DishFlavor;
import com.zyq.reggie.mapper.DishFlavorMapper;
import com.zyq.reggie.mapper.DishMapper;
import com.zyq.reggie.service.DishFlavorService;
import com.zyq.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
