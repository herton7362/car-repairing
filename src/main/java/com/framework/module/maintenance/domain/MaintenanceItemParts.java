package com.framework.module.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.framework.module.parts.domain.Parts;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@ApiModel("维修项目所需零件")
public class MaintenanceItemParts extends BaseEntity {
    @ApiModelProperty(value = "零件")
    @ManyToOne(fetch = FetchType.EAGER)
    private Parts parts;
    @ApiModelProperty(value = "数量")
    @Column(length = 11, precision = 2)
    private Double count;
    @ApiModelProperty(value = "项目")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private MaintenanceItem maintenanceItem;

    public Parts getParts() {
        return parts;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public MaintenanceItem getMaintenanceItem() {
        return maintenanceItem;
    }

    public void setMaintenanceItem(MaintenanceItem maintenanceItem) {
        this.maintenanceItem = maintenanceItem;
    }
}
