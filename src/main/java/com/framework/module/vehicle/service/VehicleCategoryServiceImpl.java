package com.framework.module.vehicle.service;

import com.framework.module.vehicle.domain.VehicleCategory;
import com.framework.module.vehicle.domain.VehicleCategoryRepository;
import com.framework.module.vehicle.web.BrandResult;
import com.framework.module.vehicle.web.TrainResult;
import com.framework.module.vehicle.web.VehicleCategoryResult;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.common.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional
public class VehicleCategoryServiceImpl extends AbstractCrudService<VehicleCategory> implements VehicleCategoryService {
    private final VehicleCategoryRepository vehicleCategoryRepository;
    @Override
    protected PageRepository<VehicleCategory> getRepository() {
        return vehicleCategoryRepository;
    }

    @Override
    public List<VehicleCategory> findAll(Map<String, String[]> param) throws Exception {
        return vehicleCategoryRepository.findAll(this.getSpecificationForAllEntities(param));
    }

    @Override
    public PageResult<VehicleCategory> findAll(PageRequest pageRequest, Map<String, String[]> param) throws Exception {
        Page<VehicleCategory> page = vehicleCategoryRepository.findAll(this.getSpecificationForAllEntities(param), pageRequest);
        return new PageResult<>(page);
    }

    @Override
    public BrandResult getVehicleBrand() throws Exception {
        Map<String, String[]> params = new HashMap<>();
        params.put("parent.id", new String[]{"isNull"});
        List<VehicleCategory> vehicleCategories = findAll(params);
        BrandResult brandResult = new BrandResult(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                // 降序排序
                return obj1.compareTo(obj2);
            }
        });
        vehicleCategories.forEach((vehicleCategory -> {
            String fistLetter = String.valueOf(vehicleCategory.getPinyin().charAt(0)).toUpperCase();
            List<VehicleCategory> vehicleCategoriesTemp;
            if(brandResult.get(fistLetter + "," + fistLetter) == null) {
                vehicleCategoriesTemp = new ArrayList<>();
                brandResult.put(fistLetter + "," + fistLetter, vehicleCategoriesTemp);
            } else {
                vehicleCategoriesTemp = brandResult.get(fistLetter + "," + fistLetter);
            }
            vehicleCategoriesTemp.add(vehicleCategory);
        }));
        return brandResult;
    }

    @Override
    public TrainResult getVehicleTrain(String brandId) throws Exception {
        Map<String, String[]> params = new HashMap<>();
        params.put("parent.id", new String[]{brandId});
        List<VehicleCategory> vehicleCategories = findAll(params);
        TrainResult trainResult = new TrainResult();
        for (VehicleCategory vehicleCategory : vehicleCategories) {
            params.put("parent.id", new String[]{vehicleCategory.getId()});
            trainResult.put(vehicleCategory.getId() + "," + vehicleCategory.getName(), findAll(params));
        }
        return trainResult;
    }

    @Autowired
    public VehicleCategoryServiceImpl(VehicleCategoryRepository vehicleCategoryRepository) {
        this.vehicleCategoryRepository = vehicleCategoryRepository;
    }
}
