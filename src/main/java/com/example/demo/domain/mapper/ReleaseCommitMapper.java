package com.example.demo.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.entity.ReleaseCommit;
import com.example.demo.domain.entity.ReleaseEnvRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 *
 * @author A80759 2024-07-08 16:47:11
 */
@Mapper
public interface ReleaseCommitMapper extends BaseMapper<ReleaseCommit> {

    @Delete(value = "delete from release_commit")
    int delete();
}
