package com.framework.module.entrustform.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("派工单参数")
public class DispatchParam {
    @ApiModelProperty(value = "班组id")
    private String workingTeamId;
    @ApiModelProperty(value = "工人id")
    private String workerId;

    public String getWorkingTeamId() {
        return workingTeamId;
    }

    public void setWorkingTeamId(String workingTeamId) {
        this.workingTeamId = workingTeamId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }
}
