package com.example.demo.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.entity.ReleaseBranch;
import com.example.demo.domain.entity.ReleaseEnv;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 *
 * @author A80759 2024-07-08 16:47:11
 */
@Mapper
public interface ReleaseEnvMapper extends BaseMapper<ReleaseEnv> {

    @Delete(value = "delete from release_env")
    int delete();
}
