package com.framework.module.workflow.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@ApiModel("工作流实例")
public class WorkflowInstance extends BaseEntity {
    @ApiModelProperty(value = "表单状态")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private State state;
    @ApiModelProperty(value = "表单id")
    @Column(length = 36)
    private String orderId;
    @ApiModelProperty(value = "审核人id")
    @Column(length = 36)
    private String auditorId;

    public enum State{
        PENDING("提交待审"),APPROVE("通过"),DISAPPROVE("未通过");
        private String name;
        State(String name) {
            this.name = name;
        }
        public String getDisplayName() {
            return name;
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
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
