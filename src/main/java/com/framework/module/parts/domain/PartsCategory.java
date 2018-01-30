package com.framework.module.parts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/**
 * 配件分类
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("零件分类")
public class PartsCategory extends BaseEntity {
    @ApiModelProperty(value = "上级分类")
    @ManyToOne(fetch = FetchType.EAGER)
    private PartsCategory parent;
    @ApiModelProperty(value = "商品分类名称")
    @Column(length = 50)
    private String name;
    @ApiModelProperty(value = "零件")
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Parts> parts;

    public PartsCategory getParent() {
        return parent;
    }

    public void setParent(PartsCategory parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parts> getParts() {
        return parts;
    }

    public void setParts(List<Parts> parts) {
        this.parts = parts;
    }
}
