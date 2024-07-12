package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("release_job")
public class ReleaseJob {

    @ApiModelProperty("")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "")
    @TableField(value = "env_id")
    private String envId;

    @ApiModelProperty(value = "")
    @TableField(value = "project_id")
    private Integer projectId;

    @TableField(value = "source_branch")
    private String sourceBranch;

    @TableField(value = "target_branch")
    private String targetBranch;
}
