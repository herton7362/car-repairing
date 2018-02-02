package com.framework.module.entrustform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.framework.module.maintenance.domain.MaintenanceItem;
import com.framework.module.parts.domain.Parts;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@ApiModel("委托书所需零件")
public class EntrustFormParts extends BaseEntity {
    @ApiModelProperty(value = "零件")
    @ManyToOne(fetch = FetchType.EAGER)
    private Parts parts;
    @ApiModelProperty(value = "委托书")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private EntrustForm entrustForm;

    public Parts getParts() {
        return parts;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }

    public EntrustForm getEntrustForm() {
        return entrustForm;
    }

    public void setEntrustForm(EntrustForm entrustForm) {
        this.entrustForm = entrustForm;
    }
}
