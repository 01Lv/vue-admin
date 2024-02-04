package com.example.demo.service.convert;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.req.AddUserReq;
import com.example.demo.domain.req.EditUserReq;
import com.example.demo.domain.resp.UserResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    List<UserResp> convertToList(List<User> list);

    @Mapping(target = "name",source = "username")
    User convert2Entity(AddUserReq req);

    User convert2Entity(EditUserReq req);
}
