package com.framework.module.maintenance.web;

import com.framework.module.maintenance.domain.MaintenanceItem;
import com.framework.module.maintenance.service.MaintenanceItemService;
import com.kratos.common.AbstractReadController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "维修项目查询")
@RestController
@RequestMapping("/maintenanceItem")
public class GuestMaintenanceItemController extends AbstractReadController<MaintenanceItem> {
    private final MaintenanceItemService maintenanceItemService;
    @Override
    protected CrudService<MaintenanceItem> getService() {
        return maintenanceItemService;
    }

    @Autowired
    public GuestMaintenanceItemController(MaintenanceItemService maintenanceItemService) {
        this.maintenanceItemService = maintenanceItemService;
    }
}
