package com.framework.module.parts.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 配件分类
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("配件分类")
public class PartsCategory extends BaseEntity {
    @ApiModelProperty(value = "上级分类")
    @ManyToOne(fetch = FetchType.EAGER)
    private PartsCategory parent;
    @ApiModelProperty(value = "商品分类名称")
    @Column(length = 50)
    private String name;

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
}
