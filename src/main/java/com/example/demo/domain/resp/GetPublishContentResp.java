package com.example.demo.domain.resp;

import com.example.demo.domain.entity.ReleaseBranch;
import com.example.demo.domain.entity.ReleaseEnv;
import lombok.Data;

import java.util.List;

@Data
public class GetPublishContentResp {

    private String prdBranch;
    private String codeRepo;
    private Integer projectId;
    private String projectName;
    private List<EnvContent> envContents;

    @Data
    public static class EnvContent extends ReleaseEnv {
        private List<ReleaseBranch> branchList;
    }
}
