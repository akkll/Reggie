package com.zyq.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyq.reggie.dto.DishDto;
import com.zyq.reggie.entity.Category;
import com.zyq.reggie.entity.Dish;
import com.zyq.reggie.entity.DishFlavor;
import com.zyq.reggie.mapper.CategoryMapper;
import com.zyq.reggie.mapper.DishMapper;
import com.zyq.reggie.service.CategoryService;
import com.zyq.reggie.service.DishFlavorService;
import com.zyq.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dto) {
        //保存菜品基本信息到菜品表dish
        this.save(dto);
        Long id = dto.getId();
        dto.getFlavors().stream().forEach((DishFlavor dishFlavor)->{
            dishFlavor.setDishId(id);
        });
        //保存菜品口味数据到菜品口味表
        dishFlavorService.saveBatch(dto.getFlavors());
    }

    @Override
    public void updateWithFlavor(DishDto dto) {
        this.updateById(dto);
        List<DishFlavor> flavors = dto.getFlavors();
        dto.getFlavors().stream().forEach((flavor)->{
            flavor.setDishId(dto.getId());
        });
        dishFlavorService.updateBatchById(flavors);
    }

    @Override
    @Transactional
    public void deleteWithFlavor(List<Long> ids) {//接受dishid
        boolean b = this.removeByIds(ids);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(queryWrapper);
    }

}
