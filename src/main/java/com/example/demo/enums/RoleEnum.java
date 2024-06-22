package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    TECHNICAL_DIRECTOR(1, "技术主管", "技术主管", "101,102,103,104"),
    TEST(2, "测试角色", "测试角色", "101,102,103,104"),;

    private final Integer id;
    private final String roleName;
    private final String desc;
    private final String rights;

    public static class RightDto{
        private String id;
        private String desc;
    }
}
