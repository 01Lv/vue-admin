package com.example.demo.domain.resp;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetPublishContentResp {

    private String prdBranch;
    private String codeRepo;
    private Integer projectId;
    private String projectName;
    private List<EnvContent> envContents;

    @Data
    public static class EnvContent{
        private String envId;
        private String envName;
        private String onlineBranch;
        private String lastUpdateBy;
        private Date lastUpdateDate;
        private String nameSpace;
        private Integer releaseStatus;
        private String releaseLogId;
        private String releaseMirror;
        private String lastModifiedBy;
        @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
        private Date lastModifiedDate;
        private List<CardContent> cardContentList;
    }

    @Data
    public static class CardContent{
        private Integer branchId;
        private String branchName;
        private Integer branchReleaseStatus;
        private String createBy;
        private Date createDate;
        private String submitPerson;
        private String submitPersonName;
        private String submitContent;
        private Date submitTime;
    }
}
