package com.framework.module.maintenance.service;

import com.framework.module.maintenance.domain.MaintenanceItem;
import com.framework.module.maintenance.domain.MaintenanceItemPartsRepository;
import com.framework.module.maintenance.domain.MaintenanceItemRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MaintenanceItemServiceImpl extends AbstractCrudService<MaintenanceItem> implements MaintenanceItemService {
    private final MaintenanceItemRepository maintenanceItemRepository;
    private final MaintenanceItemPartsRepository maintenanceItemPartsRepository;
    @Override
    protected PageRepository<MaintenanceItem> getRepository() {
        return maintenanceItemRepository;
    }

    @Override
    public MaintenanceItem save(MaintenanceItem maintenanceItem) throws Exception {
        if(maintenanceItem.getPartses() == null || maintenanceItem.getPartses().isEmpty()) {
            throw new BusinessException("配件项目不能为空");
        }
        if(StringUtils.isNotBlank(maintenanceItem.getId())) {
            MaintenanceItem old = findOne(maintenanceItem.getId());
            old.getPartses().forEach(parts -> {
                maintenanceItemPartsRepository.delete(parts);
            });
        }
        MaintenanceItem result = super.save(maintenanceItem);
        maintenanceItem.getPartses().forEach(parts -> {
            parts.setMaintenanceItem(result);
            maintenanceItemPartsRepository.save(parts);
        });
        return result;
    }

    @Autowired
    public MaintenanceItemServiceImpl(
            MaintenanceItemRepository maintenanceItemRepository,
            MaintenanceItemPartsRepository maintenanceItemPartsRepository
    ) {
        this.maintenanceItemRepository = maintenanceItemRepository;
        this.maintenanceItemPartsRepository = maintenanceItemPartsRepository;
    }
}
