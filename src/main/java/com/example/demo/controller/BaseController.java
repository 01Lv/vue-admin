package com.example.demo.controller;

import com.example.demo.common.CommonResult;
import com.example.demo.domain.req.LoginReq;
import com.example.demo.domain.resp.MenuLevel1Resp;
import com.example.demo.domain.resp.MenuLevel2Resp;
import com.example.demo.domain.resp.UserResp;
import com.example.demo.enums.MenuEnum;
import com.example.demo.enums.MenuItemEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/base")
@CrossOrigin
@Slf4j
public class BaseController {

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

    @GetMapping("/users")
    public CommonResult<List<UserResp>> users() {
        List<UserResp> result = new ArrayList<>();

        UserResp user1 = new UserResp();
        user1.setId(1);
        user1.setName("yhn");
        user1.setRole("普通用户");
        user1.setEmail("123@gmail.com");
        user1.setPhone("13544223366");
        user1.setStat(Boolean.TRUE);
        result.add(user1);

        UserResp user2 = new UserResp();
        user2.setId(2);
        user2.setName("tgb");
        user2.setRole("管理员");
        user2.setEmail("456@gmail.com");
        user2.setPhone("18955662233");
        user2.setStat(Boolean.FALSE);
        result.add(user2);

        return CommonResult.success(result, Long.valueOf(result.size()));
    }
}
