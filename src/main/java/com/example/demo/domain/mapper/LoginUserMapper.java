package com.example.demo.domain.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.entity.LoginUser;
import com.example.demo.domain.entity.Right;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginUserMapper extends BaseMapper<LoginUser> {

}
