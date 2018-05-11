package com.framework.module.vehicle.web;

import com.framework.module.vehicle.domain.VehicleCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@ApiModel("车辆品牌")
public class BrandResult extends TreeMap<String, List<VehicleCategory>> {
    public BrandResult(Comparator<String> comparator) {
        super(comparator);
    }
}
