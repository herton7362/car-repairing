package com.framework.module.workflow.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("创建工作流实例参数")
public class CreateCustomWorkflowInstanceParam {
    @ApiModelProperty(value = "表单id")
    private String orderId;
    @ApiModelProperty(value = "审核人id")
    private String auditorId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }
}
