package com.framework.module.workflow.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("指定下一审批人参数")
public class SpecifyNextAuditorParam {
    @ApiModelProperty(value = "审核人id")
    private String auditorId;

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }
}
