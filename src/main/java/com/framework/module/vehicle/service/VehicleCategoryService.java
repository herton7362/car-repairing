package com.framework.module.vehicle.service;

import com.framework.module.vehicle.domain.VehicleCategory;
import com.framework.module.vehicle.web.BrandResult;
import com.framework.module.vehicle.web.TrainResult;
import com.kratos.common.CrudService;

import java.util.List;

public interface VehicleCategoryService extends CrudService<VehicleCategory> {
    /**
     * 获取品牌
     * @return 品牌数据
     */
    BrandResult getVehicleBrand() throws Exception;

    /**
     * 获取车系
     * @param brandId 品牌id
     * @return 车系
     */
    TrainResult getVehicleTrain(String brandId) throws Exception;

}
