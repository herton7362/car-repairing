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
public class InStoreForm extends BaseEntity {
    @ApiModelProperty(value = "仓库id")
    @Column(length = 36)
    private String storeId;
    @ApiModelProperty(value = "入库时间")
    private Date inStoreDate;
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

    public Date getInStoreDate() {
        return inStoreDate;
    }

    public void setInStoreDate(Date inStoreDate) {
        this.inStoreDate = inStoreDate;
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
        IN_STORE("入库"),
        PURCHASE("采购");
        private String displayName;
        BusinessType(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
}
