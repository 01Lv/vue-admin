package com.example.demo.service.convert;

import com.example.demo.domain.resp.MenuResp;
import com.example.demo.enums.RightEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DemoConvert {

    DemoConvert INSTANCE = Mappers.getMapper(DemoConvert.class);

    List<MenuResp> convert2RespList(RightEnum[] list);
}
