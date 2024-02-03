package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.CommonResult;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.req.LoginReq;
import com.example.demo.domain.req.UserPageReq;
import com.example.demo.domain.resp.MenuLevel1Resp;
import com.example.demo.domain.resp.MenuLevel2Resp;
import com.example.demo.domain.resp.UserResp;
import com.example.demo.enums.MenuEnum;
import com.example.demo.enums.MenuItemEnum;
import com.example.demo.service.UserService;
import com.example.demo.service.convert.UserConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/base")
@CrossOrigin
@Slf4j
public class BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody LoginReq req){
        if("admin".equals(req.getUsername()) && "admin".equals(req.getPassword())){
            return CommonResult.success(UUID.randomUUID().toString());
        }else {
            return CommonResult.failed();
        }
    }

    @GetMapping("/menus")
    public CommonResult<List<MenuLevel1Resp>> menu() {

        List<MenuLevel1Resp> list = new ArrayList<>();
        for (MenuEnum each : MenuEnum.values()) {
            MenuLevel1Resp level1Resp = new MenuLevel1Resp();
            level1Resp.setId(each.getId());
            level1Resp.setName(each.getName());
            level1Resp.setPath(each.getPath());
            if (MenuItemEnum.getMenuItem(each.getId()).size() > 0) {
                List<MenuLevel2Resp> list1 = new ArrayList<>();
                for (MenuItemEnum itemEnum : MenuItemEnum.getMenuItem(each.getId())) {
                    MenuLevel2Resp level2Resp = new MenuLevel2Resp();
                    level2Resp.setId(itemEnum.getId());
                    level2Resp.setName(itemEnum.getName());
                    level2Resp.setPath(itemEnum.getPath());
                    list1.add(level2Resp);
                }
                level1Resp.setChild(list1);
            }
            list.add(level1Resp);
        }
        log.info("result: {}", list);
        return CommonResult.success(list);
    }

    @PostMapping("/users")
    public CommonResult<List<UserResp>> users(@RequestBody UserPageReq req) {

        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.like(StringUtils.hasText(req.getQuery()), User::getName, req.getQuery());
        Page<User> page = userService.page(new Page<>(req.getPageNum(), req.getPageSize()), query);
        List<UserResp> userResps = UserConvert.INSTANCE.convertToList(page.getRecords());
        return CommonResult.success(userResps, (long) userResps.size());
    }

    @PutMapping("/userStateUpdate/{id}/{stat}")
    public CommonResult<Boolean> userStateUpdate(@PathVariable("id") String id,
                                                 @PathVariable("stat") Boolean stat) {

        User user = userService.getById(id);
        user.setStat(stat);
        userService.saveOrUpdate(user);
        return CommonResult.success(Boolean.TRUE);
    }
}
