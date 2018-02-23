package com.framework.module.purchase.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.parts.domain.Parts;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@ApiModel("采购单所需零件")
public class PurchaseFormParts extends BaseEntity {
    @ApiModelProperty(value = "零件")
    @ManyToOne(fetch = FetchType.EAGER)
    private Parts parts;
    @ApiModelProperty(value = "采购单id")
    @Column(length = 36)
    private String purchaseFormId;
    @ApiModelProperty(value = "数量")
    @Column(length = 11, precision = 2)
    private Double count;

    public Parts getParts() {
        return parts;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }

    public String getPurchaseFormId() {
        return purchaseFormId;
    }

    public void setPurchaseFormId(String purchaseFormId) {
        this.purchaseFormId = purchaseFormId;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }
}
