package com.framework.module.store.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ApiModel("货物")
public class Goods extends BaseEntity {
    @ApiModelProperty(value = "仓库id")
    @Column(length = 36)
    private String storeId;
    @ApiModelProperty(value = "关联商品id")
    @Column(length = 36)
    private String relationId;
    @ApiModelProperty(value = "数量")
    @Column(length = 11, precision = 2)
    private Double count;
    @ApiModelProperty(value = "单位")
    @Column(length = 10)
    private String unit;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
