package com.example.demo.domain.entity;

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
@TableName("release_commit")
public class ReleaseCommit {

    @ApiModelProperty("")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "")
    @TableField(value = "message")
    private String message;

    @TableField(value = "committed_date")
    private Date committedDate;

    @TableField(value = "authored_date")
    private Date authoredDate;

    @TableField(value = "author_name")
    private String authorName;

    @TableField(value = "author_email")
    private String authorEmail;

}
