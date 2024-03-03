package com.zyq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyq.reggie.dto.DishDto;
import com.zyq.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dto);

    public void updateWithFlavor(DishDto dto);

    public void deleteWithFlavor(List<Long> ids);
}
