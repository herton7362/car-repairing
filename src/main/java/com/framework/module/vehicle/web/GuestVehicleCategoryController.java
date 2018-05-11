package com.framework.module.vehicle.web;

import com.framework.module.vehicle.domain.VehicleCategory;
import com.framework.module.vehicle.service.VehicleCategoryService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "游客车辆类型接口，无权限过滤")
@RestController
@RequestMapping("/vehicleCategory")
public class GuestVehicleCategoryController extends AbstractCrudController<VehicleCategory> {
    private final VehicleCategoryService vehicleCategoryService;

    @Override
    protected CrudService<VehicleCategory> getService() {
        return vehicleCategoryService;
    }

    /**
     * 获取品牌
     */
    @ApiOperation(value="获取品牌")
    @RequestMapping(value = "/brand", method = RequestMethod.GET)
    public ResponseEntity<BrandResult> getVehicleBrand() throws Exception {
        return new ResponseEntity<>(vehicleCategoryService.getVehicleBrand(), HttpStatus.OK);
    }

    /**
     * 获取车系
     */
    @ApiOperation(value="获取车系")
    @RequestMapping(value = "/train/{brandId}", method = RequestMethod.GET)
    public ResponseEntity<?> getVehicleTrain(@PathVariable String brandId) throws Exception {
        return new ResponseEntity<>(vehicleCategoryService.getVehicleTrain(brandId), HttpStatus.OK);
    }

    @Autowired
    public GuestVehicleCategoryController(VehicleCategoryService vehicleCategoryService) {
        this.vehicleCategoryService = vehicleCategoryService;
    }
}
