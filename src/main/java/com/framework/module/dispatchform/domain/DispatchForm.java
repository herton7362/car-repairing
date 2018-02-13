package com.framework.module.dispatchform.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@ApiModel("派工单")
public class DispatchForm extends BaseEntity {
    @ApiModelProperty(value = "委托书id")
    @Column(length = 36)
    private String entrustFormId;
    @ApiModelProperty(value = "委托书单号")
    @Column(length = 20)
    private String entrustFormOrderNumber;
    @ApiModelProperty(value = "派工单号")
    @Column(length = 20)
    private String orderNumber;
    @ApiModelProperty(value = "维修工人id")
    @Column(length = 36)
    private String workerId;
    @ApiModelProperty(value = "班组id")
    @Column(length = 36)
    private String workingTeamId;
    @ApiModelProperty(value = "订单状态")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;

    public String getEntrustFormId() {
        return entrustFormId;
    }

    public void setEntrustFormId(String entrustFormId) {
        this.entrustFormId = entrustFormId;
    }

    public String getEntrustFormOrderNumber() {
        return entrustFormOrderNumber;
    }

    public void setEntrustFormOrderNumber(String entrustFormOrderNumber) {
        this.entrustFormOrderNumber = entrustFormOrderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkingTeamId() {
        return workingTeamId;
    }

    public void setWorkingTeamId(String workingTeamId) {
        this.workingTeamId = workingTeamId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static enum Status {
        NEW("新建"),
        DRAWING("领料");
        private String displayName;
        Status(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
}
