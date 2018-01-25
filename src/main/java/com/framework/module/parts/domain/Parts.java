package com.framework.module.parts.domain;

import com.kratos.entity.BaseEntity;
import com.kratos.module.dictionary.domain.Dictionary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 配件
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("配件")
public class Parts extends BaseEntity {
    @ApiModelProperty(value = "配件分类")
    @ManyToOne(fetch = FetchType.EAGER)
    private PartsCategory category;
    @ApiModelProperty(value = "零件编码")
    @Column(length = 50)
    private String code;
    @ApiModelProperty(value = "零件名称")
    @Column(length = 500)
    private String name;
    @ApiModelProperty(value = "型号")
    @Column(length = 500)
    private String modelNumber;
    @ApiModelProperty(value = "规格")
    @Column(length = 100)
    private String standard;
    @ApiModelProperty(value = "产地")
    @Column(length = 100)
    private String originPlace;
    @ApiModelProperty(value = "厂牌")
    @Column(length = 100)
    private String brand;
    @ApiModelProperty(value = "单位")
    @ManyToOne(fetch = FetchType.EAGER)
    private Dictionary unit;
    @ApiModelProperty(value = "商品单价")
    @Column(length = 11, precision = 2)
    private Double price;

    public PartsCategory getCategory() {
        return category;
    }

    public void setCategory(PartsCategory category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Dictionary getUnit() {
        return unit;
    }

    public void setUnit(Dictionary unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
