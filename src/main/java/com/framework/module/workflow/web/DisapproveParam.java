package com.framework.module.workflow.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("审核不通过参数")
public class DisapproveParam {
    @ApiModelProperty(value = "审批意见")
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
