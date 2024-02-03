package com.example.demo.domain.req;

import lombok.Data;

@Data
public class UserPageReq {

    private String query;
    private Integer pageNum;
    private Integer pageSize;
}
