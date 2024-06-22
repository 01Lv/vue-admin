package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenuEnum {

    MERCHANT(1,"商户管理","/merchant",0,0),
    ORDER(2,"订单管理","/order",0,0),
    RIGHT(3,"权限管理","/right",0,0),
    COMMODITY(4,"商品管理","/commodity",0,0),;

    private final Integer id;
    private final String name;
    private final String path;
    private final Integer level;
    private final Integer pid;
}
