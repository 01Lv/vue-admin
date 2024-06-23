package com.example.demo.domain.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.entity.RoleRightRelate;
import com.example.demo.domain.entity.UserRoleRelate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleRelateMapper extends BaseMapper<UserRoleRelate> {

}
