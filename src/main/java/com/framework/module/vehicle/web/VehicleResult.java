package com.framework.module.vehicle.web;

import com.framework.module.vehicle.domain.Vehicle;
import com.framework.module.vehicle.domain.VehicleCategory;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel("车辆查询结果")
public class VehicleResult extends Vehicle {
    private List<VehicleCategory> vehicleCategories;

    public List<VehicleCategory> getVehicleCategories() {
        return vehicleCategories;
    }

    public void setVehicleCategories(List<VehicleCategory> vehicleCategories) {
        this.vehicleCategories = vehicleCategories;
    }
}
