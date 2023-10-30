package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenuEnum {

    MERCHANT(1,"商户管理","/merchant"),
    ORDER(2,"订单管理","/order"),
    RIGHT(3,"权限管理","/right"),
    COMMODITY(4,"商品管理","/commodity"),;

    private Integer id;
    private String name;
    private String path;
}
