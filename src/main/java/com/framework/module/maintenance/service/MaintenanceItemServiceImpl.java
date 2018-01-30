package com.framework.module.maintenance.service;

import com.framework.module.maintenance.domain.MaintenanceItem;
import com.framework.module.maintenance.domain.MaintenanceItemRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MaintenanceItemServiceImpl extends AbstractCrudService<MaintenanceItem> implements MaintenanceItemService {
    private final MaintenanceItemRepository maintenanceItemRepository;
    @Override
    protected PageRepository<MaintenanceItem> getRepository() {
        return maintenanceItemRepository;
    }

    @Autowired
    public MaintenanceItemServiceImpl(MaintenanceItemRepository maintenanceItemRepository) {
        this.maintenanceItemRepository = maintenanceItemRepository;
    }
}
