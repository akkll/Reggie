package com.zyq.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐菜品关系
 */
@Data
public class SetmealDish implements Serializable {
    //多对多关系，按理说只需要有setmealid和dishid就够了，但是如果还要展示name等属性就需要再查一次数据库，比较麻烦，因此再多对多的映射表可以设计几个冗余字段，例如name
    // price等，这样就免去了再查一次的问题

    private static final long serialVersionUID = 1L;

    private Long id;


    //套餐id
    private Long setmealId;


    //菜品id
    private Long dishId;


    //菜品名称 （冗余字段）
    private String name;

    //菜品原价
    private BigDecimal price;

    //份数
    private Integer copies;


    //排序
    private Integer sort;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;
}
