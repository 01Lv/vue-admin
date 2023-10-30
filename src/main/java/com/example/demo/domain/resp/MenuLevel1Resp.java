package com.example.demo.domain.resp;

import lombok.Data;

import java.util.List;

@Data
public class MenuLevel1Resp {

    private Integer id;
    private String name;
    private String path;
    private List<MenuLevel2Resp> child;
}
