package com.zyq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyq.reggie.common.R;
import com.zyq.reggie.dto.DishDto;
import com.zyq.reggie.entity.Category;
import com.zyq.reggie.entity.Dish;
import com.zyq.reggie.entity.DishFlavor;
import com.zyq.reggie.service.CategoryService;
import com.zyq.reggie.service.DishFlavorService;
import com.zyq.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    /*
    * 分页操作。
    * 这里本来返回dish即可，但是前端还要求返回category的name，dish只有category的id，category的id和name的映射还得去查一次category表
    * 这一段仔细查看
    * */
    @GetMapping("/page")
    public R<Page<DishDto>> get1(int page, int pageSize, String name){
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPage, queryWrapper);
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");//复制dishpage的全部数据给dishdtopage，除了records
        List<Dish> records = dishPage.getRecords();

//        List<DishDto> list = records.stream().map((Dish dish)->{
//            DishDto dishDto = new DishDto();
//            BeanUtils.copyProperties(dish, dishDto);
//            Category category = categoryService.getById(dish.getCategoryId());
//            dishDto.setCategoryName(category.getName());
//            return dishDto;
//        }).collect(Collectors.toList());
//        dishDtoPage.setRecords(list);

        List<Long> collect = records.stream().map((item) -> {
            return item.getCategoryId();
        }).collect(Collectors.toList());
        LambdaQueryWrapper<Category> queryWrapper1 = new LambdaQueryWrapper<>();
        List<Category> categories = categoryService.listByIds(collect);
        Map<Long, Category> map = categories.stream().collect(Collectors.toMap(Category::getId, Function.identity()));
        List<DishDto> collect1 = records.stream().map((item) -> {
            Long categoryId = item.getCategoryId();
            Category category = map.get(categoryId);
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(collect1);

        return R.success(dishDtoPage);
    }

    @PostMapping
    public R<String> post1(@RequestBody DishDto dto){
        log.info(dto.toString());
        log.info(super.toString());
        dishService.saveWithFlavor(dto);
        return R.success("成功添加");
    }

    @GetMapping("/{id}")
    public R<DishDto> get2(@PathVariable long id){
        Dish dish = dishService.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(list);
        return R.success(dishDto);
    }

    @PostMapping("/status/{status}")
    public R<String> post2(@PathVariable int status, @RequestParam List<Long> ids){
        List<Dish> dishes = dishService.listByIds(ids);
        dishes.stream().forEach((item)->{
            item.setStatus(status);
        });
        dishService.updateBatchById(dishes);
        return R.success("更新成功");
    }
    @PutMapping
    public R<String> put1(@RequestBody DishDto dto){
        dishService.updateWithFlavor(dto);
        return R.success("成功修改");
    }
    @DeleteMapping
    public R<String> delete1(@RequestParam List<Long> ids){
        dishService.deleteWithFlavor(ids);
        return R.success("成功删除");
    }

    @GetMapping("/list")
    public R<List<Dish>> get2(Dish dish){
        Long categoryId = dish.getCategoryId();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId, categoryId);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }
}
