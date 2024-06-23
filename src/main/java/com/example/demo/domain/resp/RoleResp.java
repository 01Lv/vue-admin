package com.example.demo.domain.resp;

import com.example.demo.domain.RightDto;
import lombok.Data;

import java.util.List;

@Data
public class RoleResp {

    private Integer id;
    private String roleName;
    private String desc;
    private List<RightDto> rights;
}
