package com.zyq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyq.reggie.common.R;
import com.zyq.reggie.dto.SetmealDto;
import com.zyq.reggie.entity.Category;
import com.zyq.reggie.entity.Setmeal;
import com.zyq.reggie.service.CategoryService;
import com.zyq.reggie.service.SetmealDishService;
import com.zyq.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealDishController {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<SetmealDto>> get1(int page, int pageSize, String name){
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null, Setmeal::getName, name);
        Page<Setmeal> page1 = setmealService.page(setmealPage, queryWrapper);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        BeanUtils.copyProperties(page1, setmealDtoPage, "records");
        List<Setmeal> records = page1.getRecords();
        setmealDtoPage.setRecords(records.stream().map((item)->{
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList()));
        return R.success(setmealDtoPage);
    }
}
