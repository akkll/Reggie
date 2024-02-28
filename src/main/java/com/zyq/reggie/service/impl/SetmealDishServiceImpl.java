package com.zyq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyq.reggie.entity.Setmeal;
import com.zyq.reggie.entity.SetmealDish;
import com.zyq.reggie.mapper.SetmealDishMapper;
import com.zyq.reggie.mapper.SetmealMapper;
import com.zyq.reggie.service.SetmealDishService;
import com.zyq.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
