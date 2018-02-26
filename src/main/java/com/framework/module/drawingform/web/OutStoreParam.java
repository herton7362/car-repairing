package com.framework.module.drawingform.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("领料出库参数")
public class OutStoreParam {
    @ApiModelProperty(value = "仓库id")
    private String storeId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
