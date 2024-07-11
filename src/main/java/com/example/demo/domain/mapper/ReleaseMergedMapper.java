package com.example.demo.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.entity.ReleaseMerged;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 *
 * @author A80759 2024-07-10 17:13:43
 */
@Mapper
public interface ReleaseMergedMapper extends BaseMapper<ReleaseMerged> {

    @Delete(value = "delete from release_merged")
    int delete();
}
