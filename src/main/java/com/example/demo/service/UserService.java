package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.CommonResult;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.mapper.UserMapper;
import com.example.demo.domain.req.UserPageReq;
import com.example.demo.domain.resp.UserResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    public CommonResult<List<UserResp>> page(UserPageReq req) {
        Page<User> page = this.getBaseMapper().selectPage(new Page<>(req.getPageNum(), req.getPageSize()), new LambdaQueryWrapper<User>());
        List<UserResp> collect = page.getRecords().stream().map(record -> {
            UserResp resp = new UserResp();
            BeanUtils.copyProperties(record, resp);
            return resp;
        }).collect(Collectors.toList());
        long total = page.getTotal();

        return CommonResult.success(collect, total);
    }
}
