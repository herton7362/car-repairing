package com.framework.module.workflow.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@ApiModel("工作流记录")
public class WorkflowRecord extends BaseEntity {
    @ApiModelProperty(value = "审核意见")
    @Column(length = 500)
    private String comment;
    @ApiModelProperty(value = "表单状态")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private WorkflowInstance.State state;
    @ApiModelProperty(value = "表单id")
    @Column(length = 36)
    private String orderId;
    @ApiModelProperty(value = "审核人id")
    @Column(length = 36)
    private String auditorId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public WorkflowInstance.State getState() {
        return state;
    }

    public void setState(WorkflowInstance.State state) {
        this.state = state;
    }

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
