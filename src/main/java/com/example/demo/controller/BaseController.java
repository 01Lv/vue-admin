package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.CommonResult;
import com.example.demo.domain.RightDto;
import com.example.demo.domain.entity.Right;
import com.example.demo.domain.entity.Role;
import com.example.demo.domain.entity.RoleRightRelate;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.req.AddUserReq;
import com.example.demo.domain.req.EditUserReq;
import com.example.demo.domain.req.LoginReq;
import com.example.demo.domain.req.UserPageReq;
import com.example.demo.domain.resp.*;
import com.example.demo.enums.MenuEnum;
import com.example.demo.enums.MenuItemEnum;
import com.example.demo.enums.RightEnum;
import com.example.demo.service.RightService;
import com.example.demo.service.RoleRightRelateService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.service.convert.DemoConvert;
import com.example.demo.service.convert.UserConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/base")
@CrossOrigin
@Slf4j
public class BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RightService rightService;

    @Autowired
    private RoleRightRelateService roleRightRelateService;

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

    @PostMapping("/addUser")
    public CommonResult<Integer> addUser(@RequestBody AddUserReq req) {
        User user = UserConvert.INSTANCE.convert2Entity(req);
        user.setRole(2);
        user.setStat(Boolean.FALSE);
        userService.saveOrUpdate(user);
        return CommonResult.success(user.getId());
    }

    @PutMapping("/userStateUpdate/{id}/{stat}")
    public CommonResult<Boolean> userStateUpdate(@PathVariable("id") String id,
                                                 @PathVariable("stat") Boolean stat) {

        User user = userService.getById(id);
        user.setStat(stat);
        userService.saveOrUpdate(user);
        return CommonResult.success(Boolean.TRUE);
    }

    @GetMapping("/user/{id}")
    public CommonResult<User> getUser(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return CommonResult.success(user);
    }

    @PutMapping("/editUser")
    public CommonResult<Boolean> editUser(@RequestBody EditUserReq req) {
        User user = UserConvert.INSTANCE.convert2Entity(req);
        userService.updateById(user);
        return CommonResult.success(Boolean.TRUE);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteUser(@PathVariable("id") Integer id) {
        userService.removeById(id);
        return CommonResult.success(Boolean.TRUE);
    }

    @GetMapping("/menus2")
    public CommonResult<List<MenuResp>> menus() {
        List<MenuResp> result = DemoConvert.INSTANCE.convert2RespList(RightEnum.values());
        return CommonResult.success(result);
    }

    @GetMapping("/roleList")
    public CommonResult<List<RoleResp>> roleList() {
        List<Role> list = roleService.list();
        List<Integer> roleIdList = list.stream().map(Role::getId).collect(Collectors.toList());
        Map<Integer, List<Integer>> map = roleRightRelateService.list(new LambdaQueryWrapper<RoleRightRelate>()
                        .in(RoleRightRelate::getRoleId, roleIdList)).stream()
                .collect(Collectors.groupingBy(RoleRightRelate::getRoleId,Collectors.mapping(RoleRightRelate::getRightId,Collectors.toList())));
        List<RoleResp> result = new ArrayList<>();
        for (Role role : list) {
            RoleResp resp = new RoleResp();
            resp.setId(role.getId());
            resp.setRoleName(role.getRoleName());
            resp.setDesc(role.getRemark());

            List<Integer> rightIds = map.get(role.getId());
            if (!CollectionUtils.isEmpty(rightIds)) {
                List<Right> rights = rightService.list(new LambdaQueryWrapper<Right>()
                        .in(Right::getId, rightIds));
                List<RightDto> list1 = new ArrayList<>();
                for (Right right : rights) {
                    RightDto dto = new RightDto();
                    dto.setId(right.getId());
                    dto.setDesc(right.getRemark());
                    list1.add(dto);
                }
                resp.setRights(list1);
            }
            result.add(resp);
        }
        return CommonResult.success(result);
    }

    @DeleteMapping("/roles/{roleId}/{rightId}")
    public CommonResult<Boolean> deleteRole(@PathVariable("roleId") Integer roleId,
                                            @PathVariable("rightId") Integer rightId) {

        boolean remove = roleRightRelateService.remove(new LambdaQueryWrapper<RoleRightRelate>()
                .eq(RoleRightRelate::getRoleId, roleId)
                .eq(RoleRightRelate::getRightId, rightId));
        log.info("删除角色权限结果 {}", remove);
        return CommonResult.success(remove);
    }

    @GetMapping("/rights")
    public CommonResult<List<RightDto>> rights() {
        List<RightDto> list = rightService.list().stream().map(e -> {
            RightDto dto = new RightDto();
            dto.setId(e.getId());
            dto.setDesc(e.getRemark());
            return dto;
        }).collect(Collectors.toList());
        return CommonResult.success(list);
    }

    @PostMapping("/updateRole/{roleId}")
    public CommonResult<Boolean> updateRole(@PathVariable("roleId") Integer roleId,
                                            @RequestBody List<Integer> list) {
        roleRightRelateService.remove(new LambdaQueryWrapper<RoleRightRelate>()
                .eq(RoleRightRelate::getRoleId, roleId));
        List<RoleRightRelate> collect = list.stream().map(e -> {
            RoleRightRelate relate = new RoleRightRelate();
            relate.setRoleId(roleId);
            relate.setRightId(e);
            return relate;
        }).collect(Collectors.toList());
        roleRightRelateService.saveOrUpdateBatch(collect);
        return CommonResult.success(Boolean.TRUE);
    }
}
