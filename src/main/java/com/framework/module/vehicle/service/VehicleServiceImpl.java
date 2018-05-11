package com.framework.module.vehicle.service;

import com.framework.module.vehicle.domain.Vehicle;
import com.framework.module.vehicle.domain.VehicleCategory;
import com.framework.module.vehicle.domain.VehicleRepository;
import com.framework.module.vehicle.web.VehicleResult;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.common.PageResult;
import com.kratos.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class VehicleServiceImpl extends AbstractCrudService<Vehicle> implements VehicleService {
    private VehicleRepository vehicleRepository;
    private VehicleCategoryService vehicleCategoryService;
    @Override
    protected PageRepository<Vehicle> getRepository() {
        return vehicleRepository;
    }

    @Override
    public VehicleResult findOneTranslated(String id) throws Exception {
        return translateResult(super.findOne(id));
    }

    @Override
    public PageResult<VehicleResult> findAllTranslated(PageRequest pageRequest, Map<String, String[]> param) throws Exception {
        Page<Vehicle> page = vehicleRepository.findAll(this.getSpecificationForAllEntities(param), pageRequest);
        PageResult<VehicleResult> pageResult = new PageResult<>();
        pageResult.setSize(page.getSize());
        pageResult.setTotalElements(page.getTotalElements());
        pageResult.setContent(translateResults(page.getContent()));
        return pageResult;
    }

    @Override
    public List<VehicleResult> findAllTranslated(Map<String, String[]> param) throws Exception {
        return translateResults(vehicleRepository.findAll(this.getSpecificationForAllEntities(param)));
    }

    private VehicleResult translateResult(Vehicle vehicle) throws Exception {
        VehicleResult vehicleResult = new VehicleResult();
        BeanUtils.copyProperties(vehicle, vehicleResult);
        if(StringUtils.isNotBlank(vehicle.getVehicleCategoryId())) {
            List<VehicleCategory> vehicleCategories = new ArrayList<>();
            String[] vehicleCategoryIds = vehicle.getVehicleCategoryId().split(",");
            for (String vehicleCategoryId : vehicleCategoryIds) {
                vehicleCategories.add(vehicleCategoryService.findOne(vehicleCategoryId));
            }
            vehicleResult.setVehicleCategories(vehicleCategories);
        }
        return vehicleResult;
    }

    private List<VehicleResult> translateResults(List<Vehicle> vehicles) throws Exception {
        List<VehicleResult> vehicleResults = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleResults.add(this.translateResult(vehicle));
        }
        return vehicleResults;
    }

    @Override
    public Vehicle save(Vehicle vehicle) throws Exception {
        if(StringUtils.isBlank(vehicle.getMemberId())) {
            throw new BusinessException("会员不能为空");
        }
        // 如果当前车是默认，则将其他的车改为非默认
        if(vehicle.getIsDefault()) {
            Map<String, String[]> param = new HashMap<>();
            param.put("member.id", new String[]{vehicle.getMemberId()});
            List<Vehicle> vehicles = this.findAll(param);
            vehicles.forEach(vehicle1 -> vehicle1.setIsDefault(false));
            vehicleRepository.save(vehicles);
        }
        return super.save(vehicle);
    }

    @Autowired
    public VehicleServiceImpl(
            VehicleRepository vehicleRepository,
            VehicleCategoryService vehicleCategoryService
    ) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleCategoryService = vehicleCategoryService;
    }
}
