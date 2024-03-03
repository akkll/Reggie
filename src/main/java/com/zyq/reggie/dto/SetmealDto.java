package com.zyq.reggie.dto;

import com.zyq.reggie.entity.Setmeal;
import com.zyq.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
