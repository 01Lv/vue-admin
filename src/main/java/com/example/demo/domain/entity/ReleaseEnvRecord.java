package com.example.demo.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 
 *
 * @author A80759 2024-07-08 16:47:11
 */

@Data
@ApiModel(value="")
@TableName("release_env_record")
public class ReleaseEnvRecord {

    @ApiModelProperty("id")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("")
    @TableField(value = "project_id")
    private Integer projectId;

    @ApiModelProperty(value = "线上分支")
    @TableField(value = "online_branch")
    private String onlineBranch;

    @ApiModelProperty(value = "")
    @TableField(value = "name_space")
    private String nameSpace;

    @ApiModelProperty(value = "")
    @TableField(value = "env_id")
    private String envId;

    @ApiModelProperty(value = "")
    @TableField(value = "env_name")
    private String envName;

    @ApiModelProperty(value = "")
    @TableField(value = "release_status")
    private Integer releaseStatus;

    @ApiModelProperty(value = "")
    @TableField(value = "release_log_id")
    private String releaseLogId;

    @ApiModelProperty(value = "")
    @TableField(value = "release_mirror")
    private String releaseMirror;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created_date")
    private Date createdDate;

    @ApiModelProperty(value = "修改人")
    @TableField(value = "last_modified_by")
    private String lastModifiedBy;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "last_modified_date")
    private Date lastModifiedDate;
}
