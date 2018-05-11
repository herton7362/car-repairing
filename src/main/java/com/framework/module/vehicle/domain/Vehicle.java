package com.framework.module.vehicle.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.framework.module.maintenance.domain.VehicleCategoryIdDeserializer;
import com.framework.module.maintenance.domain.VehicleCategoryIdSerializer;
import com.framework.module.member.domain.Member;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@ApiModel("车")
public class Vehicle extends BaseEntity {
    @ApiModelProperty(value = "车型id")
    @Column(length = 300)
    @JsonSerialize(using = VehicleCategoryIdSerializer.class)
    @JsonDeserialize(using = VehicleCategoryIdDeserializer.class)
    private String vehicleCategoryId;
    @ApiModelProperty(value = "发动机排量")
    @Column(length = 50)
    private String engineDisplacement;
    @ApiModelProperty(value = "生产年份")
    private Integer productionYear;
    @ApiModelProperty(value = "会员")
    @Column(length = 36)
    private String memberId;
    @ApiModelProperty(value = "是否默认")
    private Boolean isDefault;
    @ApiModelProperty(value = "车牌号")
    @Column(length = 10)
    private String plateNumber;
    @ApiModelProperty(value = "引擎号")
    @Column(length = 50)
    private String engineNumber;
    @ApiModelProperty(value = "车架号")
    @Column(length = 50)
    private String frameNumber;
    @ApiModelProperty(value = "上路时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM")
    private Date driveDate;
    @ApiModelProperty(value = "行驶里程")
    @Column(length = 11, precision = 13, scale = 2)
    private Double mileage;

    public String getVehicleCategoryId() {
        return vehicleCategoryId;
    }

    public void setVehicleCategoryId(String vehicleCategoryId) {
        this.vehicleCategoryId = vehicleCategoryId;
    }

    public String getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(String engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public Date getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(Date driveDate) {
        this.driveDate = driveDate;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }
}
