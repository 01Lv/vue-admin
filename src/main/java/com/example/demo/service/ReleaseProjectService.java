package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.entity.ReleaseBranchRecord;
import com.example.demo.domain.entity.ReleaseProject;
import com.example.demo.domain.mapper.ReleaseBranchRecordMapper;
import com.example.demo.domain.mapper.ReleaseProjectMapper;
import org.springframework.stereotype.Service;

/**
 * 应用服务默认实现
 *
 * @author A80759 2024-07-08 16:47:11
 */
@Service
public class ReleaseProjectService extends ServiceImpl<ReleaseProjectMapper, ReleaseProject> {

}
