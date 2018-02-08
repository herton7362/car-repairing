package com.framework.module.entrustform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.framework.module.maintenance.domain.MaintenanceItem;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@ApiModel("委托书维修项目")
public class EntrustFormItem extends BaseEntity {
    @ApiModelProperty(value = "维修项目")
    @ManyToOne(fetch = FetchType.EAGER)
    private MaintenanceItem maintenanceItem;
    @ApiModelProperty(value = "委托书")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private EntrustForm entrustForm;

    public MaintenanceItem getMaintenanceItem() {
        return maintenanceItem;
    }

    public void setMaintenanceItem(MaintenanceItem maintenanceItem) {
        this.maintenanceItem = maintenanceItem;
    }

    public EntrustForm getEntrustForm() {
        return entrustForm;
    }

    public void setEntrustForm(EntrustForm entrustForm) {
        this.entrustForm = entrustForm;
    }
}
