package com.framework.module.purchase.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.framework.module.entrustform.domain.EntrustForm;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@ApiModel("采购单")
public class PurchaseForm extends BaseEntity {
    @ApiModelProperty(value = "采购单号")
    @Column(length = 20)
    private String orderNumber;
    @ApiModelProperty(value = "创建人id（Admin）")
    @Column(length = 36)
    private String creatorId;
    @ApiModelProperty(value = "采购日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date purchaseDate;
    @ApiModelProperty(value = "业务员（admin表）")
    @Column(length = 36)
    private String clerkId;
    @ApiModelProperty(value = "所需零件")
    @OneToMany(mappedBy = "purchaseFormId")
    private List<PurchaseFormParts> partses;
    @ApiModelProperty(value = "订单状态")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public List<PurchaseFormParts> getPartses() {
        return partses;
    }

    public void setPartses(List<PurchaseFormParts> partses) {
        this.partses = partses;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static enum Status {
        NEW("新建"),
        IN_STORE("入库");
        private String displayName;
        Status(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
}
