package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.entity.LoginUser;
import com.example.demo.domain.entity.Right;
import com.example.demo.domain.mapper.LoginUserMapper;
import com.example.demo.domain.mapper.RightMapper;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService extends ServiceImpl<LoginUserMapper, LoginUser> {
}
