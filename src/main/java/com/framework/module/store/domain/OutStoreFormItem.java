package com.framework.module.store.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ApiModel("出库单货物")
public class OutStoreFormItem extends BaseEntity {
    @ApiModelProperty(value = "出库单id")
    @Column(length = 36)
    private String outStoreFormId;
    @ApiModelProperty(value = "关联商品id")
    @Column(length = 36)
    private String relationId;
    @ApiModelProperty(value = "数量")
    @Column(length = 11, precision = 2)
    private Double count;

    public String getOutStoreFormId() {
        return outStoreFormId;
    }

    public void setOutStoreFormId(String outStoreFormId) {
        this.outStoreFormId = outStoreFormId;
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
}
