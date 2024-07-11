package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.CommonResult;
import com.example.demo.domain.LoginUserDto;
import com.example.demo.domain.RightDto;
import com.example.demo.domain.RoleDto;
import com.example.demo.domain.entity.*;
import com.example.demo.domain.req.*;
import com.example.demo.domain.resp.*;
import com.example.demo.enums.MenuEnum;
import com.example.demo.enums.MenuItemEnum;
import com.example.demo.enums.RightEnum;
import com.example.demo.service.*;
import com.example.demo.service.convert.DemoConvert;
import com.example.demo.service.convert.UserConvert;
import com.example.demo.utils.NetworkUtils;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.Pagination;
import org.gitlab.api.models.GitlabBranch;
import org.gitlab.api.models.GitlabCommit;
import org.gitlab.api.models.GitlabMergeRequest;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.query.PaginationQuery;
import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
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
    private LoginUserService loginUserService;

    @Autowired
    private RoleRightRelateService roleRightRelateService;

    @Autowired
    private UserRoleRelateService userRoleRelateService;

    @Autowired
    private Ip2regionSearcher ip2regionSearcher;

    @Autowired
    private ReleaseEnvRecordService releaseEnvRecordService;

    @Autowired
    private ReleaseBranchRecordService releaseBranchRecordService;

    @Autowired
    private ReleaseProjectService releaseProjectService;

    @Autowired
    private ReleaseCommitService releaseCommitService;

    @Autowired
    private ReleaseBranchService releaseBranchService;

    @Autowired
    private ReleaseMergedService releaseMergedService;

    @PostMapping("/login")
    public CommonResult<LoginUser> login(HttpServletRequest httpServletRequest, @RequestBody LoginReq req){
        if("admin".equals(req.getUsername()) && "admin".equals(req.getPassword())){
            String ip = NetworkUtils.getIpAddress(httpServletRequest);
            String address = ip2regionSearcher.getAddress(ip);
            LoginUser loginUser = new LoginUser();
            loginUser.setLoginIp(ip);
            loginUser.setUserId(req.getUsername());
            loginUser.setLoginCity(address);
            loginUser.setActived(1);
            loginUser.setCreateDate(new Date());
            loginUserService.saveOrUpdate(loginUser);
            return CommonResult.success(loginUser);
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
        List<Integer> userIds = userResps.stream().map(UserResp::getId).collect(Collectors.toList());
        Map<Integer, Integer> map = userRoleRelateService.list(new LambdaQueryWrapper<UserRoleRelate>()
                .in(UserRoleRelate::getUserId, userIds)).stream().collect(Collectors.toMap(UserRoleRelate::getUserId, UserRoleRelate::getRoleId));
        Map<Integer, String> roleMap = roleService.list().stream().collect(Collectors.toMap(Role::getId, Role::getRoleName));
        for (UserResp each : userResps) {
            Integer roleId = map.get(each.getId());
            each.setRoleId(roleId);
            each.setRoleDesc(roleMap.get(roleId));
        }
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

    @GetMapping("/roles")
    public CommonResult<List<RoleDto>> roles() {
        List<RoleDto> list = roleService.list().stream().map(e -> {
            RoleDto dto = new RoleDto();
            dto.setId(e.getId());
            dto.setRoleName(e.getRoleName());
            return dto;
        }).collect(Collectors.toList());
        return CommonResult.success(list);
    }

    @GetMapping("/categoryList")
    public CommonResult<List<RoleDto>> getCategoryList(CategoryPageReq req) {

        return CommonResult.success(null);
    }

    @PostMapping("/getOnlineUserList")
    public CommonResult<List<LoginUserDto>> getOnlineUserList(@RequestBody LoginUserReq req) {

        LambdaQueryWrapper<LoginUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(req.getLoginCity()), LoginUser::getLoginCity, req.getLoginCity())
                .eq(StringUtils.hasText(req.getLoginIp()), LoginUser::getLoginIp, req.getLoginIp())
                .eq(Objects.nonNull(req.getActived()), LoginUser::getActived, req.getActived())
                .orderByDesc(LoginUser::getCreateDate);
        Page<LoginUser> page = loginUserService.page(new Page<>(req.getPageNum(), req.getPageSize()), queryWrapper);
        List<LoginUser> records = page.getRecords();
        List<LoginUserDto> result = records.stream().map(e -> {
            LoginUserDto dto = new LoginUserDto();
            BeanUtils.copyProperties(e, dto);
            return dto;
        }).collect(Collectors.toList());

        return CommonResult.success(result, page.getTotal());
    }

    @PostMapping("/updateRight/{roleId}")
    public CommonResult<Boolean> updateRight(@PathVariable("roleId") Integer roleId,
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

    @PostMapping("/updateRole/{userId}")
    public CommonResult<Boolean> updateRole(@PathVariable("userId") Integer userId,
                                             @RequestBody List<Integer> list) {
        userRoleRelateService.remove(new LambdaQueryWrapper<UserRoleRelate>()
                .eq(UserRoleRelate::getUserId, userId));
        List<UserRoleRelate> collect = list.stream().map(e -> {
            UserRoleRelate relate = new UserRoleRelate();
            relate.setUserId(userId);
            relate.setRoleId(e);
            return relate;
        }).collect(Collectors.toList());
        userRoleRelateService.saveOrUpdateBatch(collect);
        return CommonResult.success(Boolean.TRUE);
    }

    @PutMapping("/offline/{id}")
    public CommonResult<Boolean> offline(@PathVariable("id") Integer id) {
        loginUserService.update(new LambdaUpdateWrapper<LoginUser>()
                .set(LoginUser::getActived, 0)
                .eq(LoginUser::getId, id));
        return CommonResult.success(Boolean.TRUE);
    }

    @PostMapping("/batchOffline")
    public CommonResult<Boolean> batchOffline(@RequestBody List<Integer> list){
        loginUserService.update(new LambdaUpdateWrapper<LoginUser>()
                .set(LoginUser::getActived, 0)
                .in(LoginUser::getId, list));
        return CommonResult.success(Boolean.TRUE);
    }

    @PostMapping("/addRole")
    public CommonResult<Boolean> addRole(@RequestBody RoleDto req){

        Role role = new Role();
        role.setRoleName(req.getRoleName());
        role.setRemark(req.getRemark());
        roleService.saveOrUpdate(role);
        return CommonResult.success(Boolean.TRUE);
    }

    @PutMapping("/deleteRole/{id}")
    public CommonResult<Boolean> deleteRole(@PathVariable("id") Integer id) {
        roleService.removeById(id);
        return CommonResult.success(Boolean.TRUE);
    }

    @GetMapping("/release/{id}")
    public CommonResult<GetPublishContentResp> getPublishContent(@PathVariable("id") Integer id) {

        GetPublishContentResp resp = new GetPublishContentResp();

        ReleaseProject releaseProject = releaseProjectService.getById(id);
        resp.setProjectId(releaseProject.getId());
        resp.setProjectName(releaseProject.getName());
        resp.setPrdBranch(releaseProject.getDefaultBranch());
        resp.setCodeRepo(releaseProject.getWebUrl());

        return CommonResult.success(resp);
    }

    @GetMapping("/getGitlabApi")
    public void getGitlabApi() throws Exception{
        GitlabAPI connect = GitlabAPI.connect("https://ds-git.gree.com:8888/", "P_vsJmGKA8dmRA2-UWZ_");
        log.info("branches: {}");
    }

    @GetMapping("/getBranches")
    public CommonResult<List<ReleaseBranch>> getBranches() throws Exception{
        List<ReleaseBranch> list = releaseBranchService.list();
        return CommonResult.success(list);
    }

    @GetMapping("/updateProject")
    public void updateProject() throws Exception{
        GitlabAPI connect = GitlabAPI.connect("https://ds-git.gree.com:8888/", "P_vsJmGKA8dmRA2-UWZ_");
        List<GitlabProject> projects = connect.getProjects();
        projects = projects.stream()
                .filter(e->Objects.nonNull(e.getNamespace()) && e.getNamespace().getPath().equals("sms-server"))
                .filter(e->Objects.nonNull(e.getNamespace()) && e.getNamespace().getPath().equals("sparepart"))
                .collect(Collectors.toList());
        List<ReleaseProject> releaseProjects = new ArrayList<>();
        for (GitlabProject project : projects) {
            ReleaseProject releaseProject = new ReleaseProject();
            releaseProject.setId(project.getId());
            releaseProject.setName(project.getName());
            releaseProject.setDefaultBranch(project.getDefaultBranch());
            releaseProject.setWebUrl(project.getWebUrl());
            releaseProject.setHttpUrl(project.getHttpUrl());
            releaseProject.setCreatedAt(project.getCreatedAt());
            releaseProject.setLastActivityAt(project.getLastActivityAt());
            releaseProject.setNamespaceId(project.getNamespace().getId());
            releaseProject.setNamespaceName(project.getNamespace().getName());
            releaseProject.setNamespacePath(project.getNamespace().getPath());
            releaseProject.setNamespaceParentId(project.getNamespace().getParentId());

            releaseProjects.add(releaseProject);
        }
        releaseProjectService.getBaseMapper().delete();
        releaseProjectService.saveOrUpdateBatch(releaseProjects);
    }

    public void getBranches(Integer projectId) throws Exception{
        GitlabAPI connect = GitlabAPI.connect("https://ds-git.gree.com:8888/", "P_vsJmGKA8dmRA2-UWZ_");
        List<GitlabBranch> branches = connect.getBranches(projectId);
        List<ReleaseBranch> releaseBranches = new ArrayList<>();
        for (GitlabBranch branch : branches) {
            ReleaseBranch releaseBranch = new ReleaseBranch();
            releaseBranch.setId(branch.getCommit().getId());
            releaseBranch.setProjectId(projectId);
            releaseBranch.setName(branch.getName());
            releaseBranch.setMessage(branch.getCommit().getMessage());
            releaseBranch.setCommittedDate(branch.getCommit().getCommittedDate());
            releaseBranch.setAuthorName(connect.getLastCommits(projectId,branch.getName()).get(0).getAuthorName());
            releaseBranch.setAuthorEmail(connect.getLastCommits(projectId,branch.getName()).get(0).getAuthorEmail());
            releaseBranches.add(releaseBranch);
        }
        releaseBranchService.getBaseMapper().delete();
        releaseBranchService.saveOrUpdateBatch(releaseBranches);
    }

    public void getCommits(Integer projectId) throws Exception{
        GitlabAPI connect = GitlabAPI.connect("https://ds-git.gree.com:8888/", "P_vsJmGKA8dmRA2-UWZ_");
        List<GitlabCommit> lastCommits = connect.getLastCommits(projectId);
        List<ReleaseCommit> commits = new ArrayList<>();
        for (GitlabCommit each : lastCommits) {
            ReleaseCommit commit = new ReleaseCommit();
            commit.setId(each.getId());
            commit.setMessage(each.getMessage());
            commit.setAuthoredDate(each.getAuthoredDate());
            commit.setCommittedDate(each.getCommittedDate());
            commit.setAuthorName(each.getAuthorName());
            commit.setAuthorEmail(each.getAuthorEmail());
            commits.add(commit);
        }
        releaseCommitService.getBaseMapper().delete();
        releaseCommitService.saveOrUpdateBatch(commits);
    }

    public void getMerged(Integer projectId) throws Exception{
        GitlabAPI connect = GitlabAPI.connect("https://ds-git.gree.com:8888/", "P_vsJmGKA8dmRA2-UWZ_");
        List<GitlabMergeRequest> mergedMergeRequests = connect.getMergedMergeRequests(projectId);
        List<GitlabMergeRequest> collect = mergedMergeRequests.stream()
                .collect(Collectors.groupingBy(GitlabMergeRequest::getTargetBranch))
                .values().stream().map(e -> e.get(0)).collect(Collectors.toList());
        List<ReleaseMerged> mergeds = new ArrayList<>();
        for (GitlabMergeRequest each : collect) {
            ReleaseMerged entity = new ReleaseMerged();
            entity.setId(each.getId());
            entity.setProjectId(each.getProjectId());
            entity.setDescription(each.getDescription());
            entity.setState(each.getState());
            entity.setSourceBranch(each.getSourceBranch());
            entity.setTargetBranch(each.getTargetBranch());
            entity.setMergeBy(each.getMergedBy().getName());
            entity.setMergedAt(each.getMergedAt());
            mergeds.add(entity);
        }
        releaseMergedService.getBaseMapper().delete();
        releaseMergedService.saveOrUpdateBatch(mergeds);
    }
}
