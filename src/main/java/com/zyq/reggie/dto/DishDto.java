package com.zyq.reggie.dto;

import com.zyq.reggie.entity.Dish;
import com.zyq.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

    @Override
    public String toString() {
        return "DishDto{" +
                "flavors=" + flavors +
                ", categoryName='" + categoryName + '\'' +
                ", copies=" + copies +
                '}'+"---"+super.toString();
    }
}
