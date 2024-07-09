package com.example.demo.domain.entity;

import java.util.Date;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import io.swagger.annotations.ApiModel;
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
@TableName("release_branch_record")
public class ReleaseBranchRecord{

    @ApiModelProperty("")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "")
    @TableField(value = "env_id")
    private String envId;

    @ApiModelProperty(value = "")
    @TableField(value = "env_name")
    private String envName;

    @ApiModelProperty(value = "")
    @TableField(value = "project_id")
    private Integer projectId;

    @ApiModelProperty("")
    @TableField(value = "branch_id")
    private Integer branchId;

    @ApiModelProperty(value = "")
    @TableField(value = "branch_name")
    private String branchName;

    @ApiModelProperty(value = "")
    @TableField(value = "release_status")
    private Integer releaseStatus;

    @ApiModelProperty(value = "")
    @TableField(value = "submit_person")
    private String submitPerson;

    @ApiModelProperty(value = "")
    @TableField(value = "submit_person_name")
    private String submitPersonName;

    @ApiModelProperty(value = "")
    @TableField(value = "submit_content")
    private String submitContent;

    @ApiModelProperty(value = "")
    @TableField(value = "submit_time")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date submitTime;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "created_by")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created_date")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date createdDate;
}
