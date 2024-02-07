package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RightEnum {

    MERCHANT("商户管理", 101, 0, "merchant", 0),
    ORDER("订单管理", 102, 1, "order", 0),
    RIGHTS("权限管理", 103, 2, "rights", 0),
    COMMODITY("商品管理", 104, 0, "commodity", 0),;

    private final String authName;
    private final Integer id;
    private final Integer level;
    private final String path;
    private final Integer pid;
}
