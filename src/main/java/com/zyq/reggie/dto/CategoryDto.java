package com.zyq.reggie.dto;

import com.zyq.reggie.entity.Category;
import lombok.Data;

@Data
public class CategoryDto extends Category {
    private Integer page;
    private Integer pageSize;
}
