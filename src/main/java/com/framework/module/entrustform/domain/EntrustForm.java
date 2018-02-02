package com.framework.module.entrustform.domain;

import com.framework.module.vehicle.domain.Vehicle;
import com.kratos.entity.BaseEntity;
import com.kratos.module.auth.domain.Admin;
import com.kratos.module.dictionary.domain.Dictionary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@ApiModel("委托书")
public class EntrustForm extends BaseEntity {
    @ApiModelProperty(value = "委托单号")
    @Column(length = 20)
    private String orderNumber;
    @ApiModelProperty(value = "车辆")
    @ManyToOne(fetch = FetchType.EAGER)
    private Vehicle vehicle;
    @ApiModelProperty(value = "创建人")
    @ManyToOne(fetch = FetchType.EAGER)
    private Admin creator;
    @ApiModelProperty(value = "进场时间")
    private Long approachDate;
    @ApiModelProperty(value = "保险公司")
    @ManyToOne(fetch = FetchType.EAGER)
    private Dictionary insuranceCompany;
    @ApiModelProperty(value = "计划完工时间")
    private Long planFinishDate;
    @ApiModelProperty(value = "接车员")
    @ManyToOne(fetch = FetchType.EAGER)
    private Admin operator;
    @ApiModelProperty(value = "公里数")
    @Column(length = 11, precision = 2)
    private Double kilometres;
    @ApiModelProperty(value = "业务员")
    @ManyToOne(fetch = FetchType.EAGER)
    private Admin clerk;
    @ApiModelProperty(value = "维修项目")
    @OneToMany(mappedBy = "entrustForm")
    private List<EntrustFormItem> items;
    @ApiModelProperty(value = "所需零件")
    @OneToMany(mappedBy = "entrustForm")
    private List<EntrustFormParts> partses;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Admin getCreator() {
        return creator;
    }

    public void setCreator(Admin creator) {
        this.creator = creator;
    }

    public Long getApproachDate() {
        return approachDate;
    }

    public void setApproachDate(Long approachDate) {
        this.approachDate = approachDate;
    }

    public Dictionary getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(Dictionary insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public Long getPlanFinishDate() {
        return planFinishDate;
    }

    public void setPlanFinishDate(Long planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public Admin getOperator() {
        return operator;
    }

    public void setOperator(Admin operator) {
        this.operator = operator;
    }

    public Double getKilometres() {
        return kilometres;
    }

    public void setKilometres(Double kilometres) {
        this.kilometres = kilometres;
    }

    public Admin getClerk() {
        return clerk;
    }

    public void setClerk(Admin clerk) {
        this.clerk = clerk;
    }

    public List<EntrustFormItem> getItems() {
        return items;
    }

    public void setItems(List<EntrustFormItem> items) {
        this.items = items;
    }

    public List<EntrustFormParts> getPartses() {
        return partses;
    }

    public void setPartses(List<EntrustFormParts> partses) {
        this.partses = partses;
    }
}
