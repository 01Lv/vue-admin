package com.example.demo.domain.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author A80759 2024-07-08 16:47:11
 */

@Data
@ApiModel(value="")
@TableName("release_env")
public class ReleaseEnv {

    @ApiModelProperty("")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "")
    @TableField(value = "project_id")
    private Integer projectId;

    @TableField(value = "env_id")
    private String envId;

    @ApiModelProperty(value = "")
    @TableField(value = "online_branch")
    private String onlineBranch;

    @TableField(value = "last_update_by")
    private String lastUpdateBy;

    @TableField(value = "last_update_at")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date lastUpdateAt;

    @TableField(value = "release_status")
    private Integer releaseStatus;
}
