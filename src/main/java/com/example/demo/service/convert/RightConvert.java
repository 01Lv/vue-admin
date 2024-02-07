package com.example.demo.service.convert;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.req.AddUserReq;
import com.example.demo.domain.req.EditUserReq;
import com.example.demo.domain.resp.RightResp;
import com.example.demo.domain.resp.UserResp;
import com.example.demo.enums.RightEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RightConvert {

    RightConvert INSTANCE = Mappers.getMapper(RightConvert.class);

    List<RightResp> convert2RespList(RightEnum[] list);
}
