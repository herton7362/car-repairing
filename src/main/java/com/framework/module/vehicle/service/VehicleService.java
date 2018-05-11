package com.framework.module.vehicle.service;

import com.framework.module.vehicle.domain.Vehicle;
import com.framework.module.vehicle.web.VehicleResult;
import com.kratos.common.CrudService;
import com.kratos.common.PageResult;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface VehicleService extends CrudService<Vehicle> {
    /**
     * 查询并翻译其中的关联id
     * @param id 车辆id
     * @return 结果
     */
    VehicleResult findOneTranslated(String id) throws Exception;

    /**
     * 分页查询并翻译其中的关联id
     * @param pageRequest 分页参数
     * @param param 参数
     * @return 结果集
     */
    PageResult<VehicleResult> findAllTranslated(PageRequest pageRequest, Map<String, String[]> param) throws Exception;

    /**
     * 不分页查询并翻译其中的关联id
     * @param param 参数
     * @return 结果集
     */
    List<VehicleResult> findAllTranslated(Map<String, String[]> param) throws Exception;
}
