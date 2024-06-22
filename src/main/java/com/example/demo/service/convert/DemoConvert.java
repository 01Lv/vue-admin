package com.example.demo.service.convert;

import com.example.demo.domain.resp.RightResp;
import com.example.demo.domain.resp.RoleResp;
import com.example.demo.enums.RightEnum;
import com.example.demo.enums.RoleEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DemoConvert {

    DemoConvert INSTANCE = Mappers.getMapper(DemoConvert.class);

    List<RightResp> convert2RespList(RightEnum[] list);

    List<RoleResp> convert2RoleList(RoleEnum[] list);
}
