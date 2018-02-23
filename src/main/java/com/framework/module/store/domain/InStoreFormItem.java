package com.framework.module.store.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ApiModel("入库单货物")
public class InStoreFormItem extends BaseEntity {
    @ApiModelProperty(value = "入库单id")
    @Column(length = 36)
    private String inStoreFormId;
    @ApiModelProperty(value = "关联商品id")
    @Column(length = 36)
    private String relationId;
    @ApiModelProperty(value = "数量")
    @Column(length = 11, precision = 2)
    private Double count;

    public String getInStoreFormId() {
        return inStoreFormId;
    }

    public void setInStoreFormId(String inStoreFormId) {
        this.inStoreFormId = inStoreFormId;
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
