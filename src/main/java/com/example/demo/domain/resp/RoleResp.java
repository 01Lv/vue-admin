package com.example.demo.domain.resp;

import lombok.Data;

@Data
public class RoleResp {

    private final Integer id;
    private final String roleName;
    private final String desc;
    private final String rights;
}
