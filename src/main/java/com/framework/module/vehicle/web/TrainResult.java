package com.framework.module.vehicle.web;

import com.framework.module.vehicle.domain.VehicleCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.List;


@ApiModel("车系")
public class TrainResult  extends HashMap<String, List<VehicleCategory>> {
}
