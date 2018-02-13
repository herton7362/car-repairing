package com.framework.module.drawingform.domain;

import com.framework.module.dispatchform.domain.DispatchForm;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@ApiModel("领料单")
public class DrawingForm extends BaseEntity {
    @ApiModelProperty(value = "委托书id")
    @Column(length = 36)
    private String entrustFormId;
    @ApiModelProperty(value = "委托书单号")
    @Column(length = 20)
    private String entrustFormOrderNumber;
    @ApiModelProperty(value = "领料单号")
    @Column(length = 20)
    private String orderNumber;
    @ApiModelProperty(value = "领料工人id")
    @Column(length = 36)
    private String workerId;
    @ApiModelProperty(value = "领料班组id")
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
        OUT_STORE("出库");
        private String displayName;
        Status(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
}
