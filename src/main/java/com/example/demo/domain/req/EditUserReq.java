package com.example.demo.domain.req;

import lombok.Data;

@Data
public class EditUserReq {
    private Integer id;
    private String name;
    private String email;
    private String phone;
}
