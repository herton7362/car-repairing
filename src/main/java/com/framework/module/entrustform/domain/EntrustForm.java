package com.framework.module.entrustform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.framework.module.vehicle.domain.Vehicle;
import com.kratos.entity.BaseEntity;
import com.kratos.module.auth.domain.Admin;
import com.kratos.module.dictionary.domain.Dictionary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
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
    @Column(length = 36)
    private String creatorId;
    @ApiModelProperty(value = "进场时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date approachDate;
    @ApiModelProperty(value = "保险公司（字典表）")
    @Column(length = 36)
    private String insuranceCompanyId;
    @ApiModelProperty(value = "计划完工时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date planFinishDate;
    @ApiModelProperty(value = "接车员（admin表）")
    @Column(length = 36)
    private String operatorId;
    @ApiModelProperty(value = "公里数")
    @Column(length = 11, precision = 2)
    private Double kilometres;
    @ApiModelProperty(value = "业务员（admin表）")
    @Column(length = 36)
    private String clerkId;
    @ApiModelProperty(value = "维修项目")
    @OneToMany(mappedBy = "entrustForm")
    private List<EntrustFormItem> items;
    @ApiModelProperty(value = "所需零件")
    @OneToMany(mappedBy = "entrustForm")
    private List<EntrustFormParts> partses;
    @ApiModelProperty(value = "联系电话")
    @Column(length = 20)
    private String contactTel;
    @ApiModelProperty(value = "订单状态")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;
    @ApiModelProperty("实付")
    @Column(length = 11, precision = 2)
    private Double finalPay;
    @ApiModelProperty("现金支付")
    @Column(length = 11, precision = 2)
    private Double cashPay;
    @ApiModelProperty("余额支付金额")
    @Column(length = 11, precision = 2)
    private Double balancePay;
    @ApiModelProperty(value = "支付方式")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private PayType payType;
    @ApiModelProperty(value = "支付状态")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;
    @ApiModelProperty(value = "来源")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Origin origin;

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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getApproachDate() {
        return approachDate;
    }

    public void setApproachDate(Date approachDate) {
        this.approachDate = approachDate;
    }

    public Date getPlanFinishDate() {
        return planFinishDate;
    }

    public void setPlanFinishDate(Date planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public String getInsuranceCompanyId() {
        return insuranceCompanyId;
    }

    public void setInsuranceCompanyId(String insuranceCompanyId) {
        this.insuranceCompanyId = insuranceCompanyId;
    }

    public Double getKilometres() {
        return kilometres;
    }

    public void setKilometres(Double kilometres) {
        this.kilometres = kilometres;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
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

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getFinalPay() {
        return finalPay;
    }

    public void setFinalPay(Double finalPay) {
        this.finalPay = finalPay;
    }

    public Double getCashPay() {
        return cashPay;
    }

    public void setCashPay(Double cashPay) {
        this.cashPay = cashPay;
    }

    public Double getBalancePay() {
        return balancePay;
    }

    public void setBalancePay(Double balancePay) {
        this.balancePay = balancePay;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public static enum Status {
        NEW("新建"),
        CONFIRM("确认"),
        DISPATCHING("派工"),
        FINISHED("竣工");
        private String displayName;
        Status(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }

    public static enum PayType {
        ONLINE("线上"),
        OFFLINE("线下");
        private String displayName;
        PayType(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }

    public static enum Origin {
        LOCAL("本地创建"),
        ONLINE_SHOP("线上商城");
        private String displayName;
        Origin(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }

    public static enum PayStatus {
        UNPAY("未支付"),
        PAYED("已支付");
        private String displayName;
        PayStatus(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
}
