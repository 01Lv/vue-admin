package com.example.demo.domain.req;

import lombok.Data;

@Data
public class LoginUserReq {

    private String loginCity;
    private String loginIp;
    private Integer actived;
    private Integer pageNum;
    private Integer pageSize;
}
