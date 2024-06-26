package com.example.demo.domain;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String userId;
    private String loginIp;
    private String loginCity;
    private Integer actived;
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date createDate;


}
