package com.framework.module.store.service;

import com.framework.module.store.domain.InStoreForm;
import com.framework.module.store.domain.InStoreFormRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional
public class InStoreFormServiceImpl extends AbstractCrudService<InStoreForm> implements InStoreFormService {
    private final InStoreFormRepository inStoreFormRepository;
    private final StoreService storeService;
    @Override
    protected PageRepository<InStoreForm> getRepository() {
        return inStoreFormRepository;
    }

    @Override
    public InStoreForm save(InStoreForm inStoreForm) throws Exception {
        inStoreForm.setInStoreDate(new Date());
        storeService.inStore(inStoreForm.getStoreId(), inStoreForm.getRelationId(), inStoreForm.getCount());
        return super.save(inStoreForm);
    }

    @Autowired
    public InStoreFormServiceImpl(
            InStoreFormRepository inStoreFormRepository,
            StoreService storeService
    ) {
        this.inStoreFormRepository = inStoreFormRepository;
        this.storeService = storeService;
    }
}
