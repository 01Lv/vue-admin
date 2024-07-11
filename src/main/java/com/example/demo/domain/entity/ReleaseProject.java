package com.example.demo.domain.entity;

import cn.hutool.core.date.DatePattern;
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
@TableName("release_project")
public class ReleaseProject {

    @ApiModelProperty("")
    @TableId(value = "id")
    private Integer id;

    @ApiModelProperty(value = "")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "")
    @TableField(value = "default_branch")
    private String defaultBranch;

    @TableField(value = "web_url")
    private String webUrl;

    @TableField(value = "http_url")
    private String httpUrl;

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "last_activity_at")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date lastActivityAt;

    @TableField(value = "namespace_id")
    private Integer namespaceId;

    @TableField(value = "namespace_name")
    private String namespaceName;

    @TableField(value = "namespace_path")
    private String namespacePath;

    @TableField(value = "namespace_parent_id")
    private String namespaceParentId;
}
