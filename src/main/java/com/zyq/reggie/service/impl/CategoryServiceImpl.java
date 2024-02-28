package com.zyq.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyq.reggie.common.CustomException;
import com.zyq.reggie.entity.Category;
import com.zyq.reggie.entity.Dish;
import com.zyq.reggie.entity.Employee;
import com.zyq.reggie.entity.Setmeal;
import com.zyq.reggie.mapper.CategoryMapper;
import com.zyq.reggie.mapper.EmployeeMapper;
import com.zyq.reggie.service.CategoryService;
import com.zyq.reggie.service.DishService;
import com.zyq.reggie.service.EmployeeService;
import com.zyq.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /*
    * 根据id删除套餐，删除之前要先判断与菜品和套餐的关联情况
    * */
    @Override
    public void remove(String id) {
//        查询当前分类是否关联菜品，如果已有关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count>0){
            throw new CustomException("当前分类已与餐品关联，不能删除");
        }
//        查询当前分类是否关联套餐，如果已有关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1>0){
            throw new CustomException("当前分类已与套餐关联，不能删除");
        }
//        正常删除
        super.removeById(id);

    }
}
