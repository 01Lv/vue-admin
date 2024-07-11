package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.entity.ReleaseBranch;
import com.example.demo.domain.entity.ReleaseCommit;
import com.example.demo.domain.mapper.ReleaseBranchMapper;
import com.example.demo.domain.mapper.ReleaseCommitMapper;
import org.springframework.stereotype.Service;

/**
 * 应用服务默认实现
 *
 * @author A80759 2024-07-08 16:47:11
 */
@Service
public class ReleaseBranchService extends ServiceImpl<ReleaseBranchMapper, ReleaseBranch> {

}
