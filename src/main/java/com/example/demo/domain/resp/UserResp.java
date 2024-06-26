package com.example.demo.domain.resp;

import lombok.Data;

import java.util.List;

@Data
public class UserResp {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Boolean stat;
    private Integer roleId;
    private String roleDesc;
}
