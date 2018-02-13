package com.framework.module.store.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ApiModel("仓库")
public class Store extends BaseEntity {
    @ApiModelProperty(value = "仓库名称")
    @Column(length = 100)
    private String name;
    @ApiModelProperty(value = "仓库代码")
    @Column(length = 100)
    private String code;
    @ApiModelProperty(value = "仓库管理员")
    @Column(length = 36)
    private String adminId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
