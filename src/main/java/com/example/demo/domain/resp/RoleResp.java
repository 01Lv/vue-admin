package com.example.demo.domain.resp;

import lombok.Data;

import java.util.List;

@Data
public class RoleResp {

    private Integer id;
    private String roleName;
    private String desc;
    private List<RightDto> rights;

    @Data
    public static class RightDto{
        private Integer id;
        private String desc;
    }
}
