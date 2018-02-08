package com.framework.module.maintenance.domain;

import com.framework.module.parts.domain.Parts;
import com.kratos.entity.BaseEntity;
import com.kratos.module.auth.domain.Admin;
import com.kratos.module.dictionary.domain.Dictionary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@ApiModel(value = "维修项目")
public class MaintenanceItem extends BaseEntity {
    @ApiModelProperty("名称")
    @Column(length = 100)
    private String name;
    @ApiModelProperty("工种")
    @ManyToOne(fetch = FetchType.EAGER)
    private Dictionary workType;
    @ApiModelProperty("工时金额")
    @Column(length = 11, precision = 2)
    private Double manHourPrice;
    @ApiModelProperty("班组")
    @ManyToOne(fetch = FetchType.EAGER)
    private WorkingTeam workingTeam;
    @ApiModelProperty(value = "所需零件")
    @OneToMany(mappedBy = "maintenanceItem")
    @Where(clause="logically_deleted=0")
    private List<MaintenanceItemParts> partses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dictionary getWorkType() {
        return workType;
    }

    public void setWorkType(Dictionary workType) {
        this.workType = workType;
    }

    public Double getManHourPrice() {
        return manHourPrice;
    }

    public void setManHourPrice(Double manHourPrice) {
        this.manHourPrice = manHourPrice;
    }

    public WorkingTeam getWorkingTeam() {
        return workingTeam;
    }

    public void setWorkingTeam(WorkingTeam workingTeam) {
        this.workingTeam = workingTeam;
    }

    public List<MaintenanceItemParts> getPartses() {
        return partses;
    }

    public void setPartses(List<MaintenanceItemParts> partses) {
        this.partses = partses;
    }
}
