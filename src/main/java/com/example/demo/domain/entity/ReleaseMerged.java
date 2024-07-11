package com.example.demo.domain.entity;

import java.util.Date;
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
 * @author A80759 2024-07-10 17:13:43
 */

@Data
@ApiModel(value="")
@TableName("release_merged")
public class ReleaseMerged{

    @ApiModelProperty("")
    @TableId(value = "id")
    private Integer id;

    @ApiModelProperty(value = "")
    @TableField(value = "project_id")
    private Integer projectId;

    @ApiModelProperty(value = "")
    @TableField(value = "state")
    private String state;

    @ApiModelProperty(value = "")
    @TableField(value = "description")
    private String description;

    @ApiModelProperty(value = "")
    @TableField(value = "merged")
    private Integer merged;

    @ApiModelProperty(value = "")
    @TableField(value = "merge_by")
    private String mergeBy;

    @ApiModelProperty(value = "")
    @TableField(value = "merged_at")
    private Date mergedAt;

    @ApiModelProperty(value = "")
    @TableField(value = "target_branch")
    private String targetBranch;

    @ApiModelProperty(value = "")
    @TableField(value = "source_branch")
    private String sourceBranch;
}
