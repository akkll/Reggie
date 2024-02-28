package com.zyq.reggie.dto;

import com.zyq.reggie.entity.User;
import lombok.Data;

@Data
public class UserDto extends User {
    private String code;
}
