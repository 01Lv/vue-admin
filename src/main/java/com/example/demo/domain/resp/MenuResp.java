package com.example.demo.domain.resp;

import lombok.Data;

@Data
public class MenuResp {

    private String name;
    private Integer level;
    private Integer id;
    private String path;
    private Integer pid;
}
