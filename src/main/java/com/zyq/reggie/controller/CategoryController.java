package com.zyq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyq.reggie.common.R;
import com.zyq.reggie.dto.CategoryDto;
import com.zyq.reggie.entity.Category;
import com.zyq.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<Category>> page1(int page,int pageSize){
        Page<Category> categoryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        Page<Category> page1 = categoryService.page(categoryPage, queryWrapper);
        return R.success(page1);
    }

    @PostMapping
    public R<String> post1(@RequestBody Category category){
        categoryService.save(category);
        return R.success("成功添加");
    }
    @DeleteMapping
    public R<String> delete1(String ids){
//        categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }
    @PutMapping
    public R<String> put1(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("成功修改");
    }
//    @GetMapping("/list")
//    public R<List<Category>> get1(CategoryDto category){
//        //这里不能加@RequestBody，因为@RequestBody是用来接收请求体里面的数据的，而不是地址栏里面的数据
//        //地址栏里的数据需要使用@RequestParam来接受或者使用形参
//        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(category.getType()!=null,Category::getType, category.getType());
//        //queryWrapper.eq(type!=null,Category::getType, type);
//        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
//        List<Category> list = categoryService.list(queryWrapper);
//        return R.success(list);
//    }
    @GetMapping("/list")
    public R<List<Category>> get1(CategoryDto category){
        Page<Category> categoryPage = null;
        if (category.getPage()!=null && category.getPageSize()!=null){
            categoryPage = new Page<>(category.getPage(), category.getPageSize());
        } else {
            categoryPage = new Page<>();
        }
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null,Category::getType, category.getType());
        //queryWrapper.eq(type!=null,Category::getType, type);
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        Page<Category> page = categoryService.page(categoryPage, queryWrapper);
        return R.success(page.getRecords());
    }

}
