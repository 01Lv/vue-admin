package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public enum MenuItemEnum {

    MERCHANT_1(11,"商户管理子11","/merchant11",1),
    MERCHANT_2(12,"商户管理子12","/merchant12",1),
    COMMODITY_1(41,"商品管理子41","/commodity41",4),
    COMMODITY_2(42,"商品管理子42","/commodity42",4),;

    private Integer id;
    private String name;
    private String path;
    private Integer parentId;

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
