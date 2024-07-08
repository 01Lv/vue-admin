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
@TableName("release_project")
public class ReleaseProject {

    @ApiModelProperty("")
    @TableId(value = "project_id",type = IdType.AUTO)
    private Integer projectId;

    @ApiModelProperty(value = "")
    @TableField(value = "project_name")
    private String projectName;

    @ApiModelProperty(value = "")
    @TableField(value = "prd_branch")
    private String prdBranch;
}
