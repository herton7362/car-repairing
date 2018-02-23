package com.framework.module.store.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Entity
@ApiModel("入库单")
public class OutStoreForm extends BaseEntity {
    @ApiModelProperty(value = "仓库id")
    @Column(length = 36)
    private String storeId;
    @ApiModelProperty(value = "出库时间")
    private Date outStoreDate;
    @ApiModelProperty(value = "操作员（admin表）")
    @Column(length = 36)
    private String operatorId;
    @ApiModelProperty(value = "业务类型")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Date getOutStoreDate() {
        return outStoreDate;
    }

    public void setOutStoreDate(Date outStoreDate) {
        this.outStoreDate = outStoreDate;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public static enum BusinessType {
        OUT_STORE("出库"),
        DRAWING("领料");
        private String displayName;
        BusinessType(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
}
