package com.framework.module.vehicle.web;

import com.framework.module.vehicle.domain.Vehicle;
import com.framework.module.vehicle.service.VehicleService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import com.kratos.common.PageParam;
import com.kratos.common.PageResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "车辆管理")
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController extends AbstractCrudController<Vehicle> {
    private VehicleService vehicleService;
    @Override
    protected CrudService<Vehicle> getService() {
        return vehicleService;
    }

    public ResponseEntity<?> searchPagedList(@ModelAttribute PageParam pageParam, HttpServletRequest request) throws Exception {
        Map<String, String[]> param = request.getParameterMap();
        if(pageParam.isPageAble()) {
            PageResult<VehicleResult> page = vehicleService.findAllTranslated(pageParam.getPageRequest(), param);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        List<VehicleResult> list = vehicleService.findAllTranslated(param);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<VehicleResult> getOne(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(vehicleService.findOneTranslated(id), HttpStatus.OK);
    }

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
}
