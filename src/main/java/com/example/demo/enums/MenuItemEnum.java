package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public enum MenuItemEnum {

    MERCHANT_1(11,"用户管理","/user",1),
    MERCHANT_2(12,"在线用户","/onlineUser",1),
    RIGHT_1(21,"角色列表","/roles",3),
    RIGHT_2(22,"权限列表","/rights",3),
    COMMODITY_1(41,"商品列表","/commodity41",4),
    COMMODITY_2(42,"分类参数","/commodity42",4),
    COMMODITY_3(43,"商品分类","/category",4),
    PROJECT_1(51,"项目列表","/project",5),;

    private final Integer id;
    private final String name;
    private final String path;
    private final Integer parentId;

    public static List<MenuItemEnum> getMenuItem(Integer parentId) {

        List<MenuItemEnum> list = new ArrayList<>();
        for (MenuItemEnum each : MenuItemEnum.values()) {
            if (each.getParentId().equals(parentId)) {
                list.add(each);
            }
        }
        return list;
    }
}
