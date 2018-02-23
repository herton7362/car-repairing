package com.framework.module.store.service;

import com.framework.module.store.domain.InStoreFormItem;
import com.framework.module.store.domain.InStoreFormItemRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class InStoreFormItemServiceImpl extends AbstractCrudService<InStoreFormItem> implements InStoreFormItemService {
    private final InStoreFormItemRepository inStoreFormItemRepository;
    @Override
    protected PageRepository<InStoreFormItem> getRepository() {
        return inStoreFormItemRepository;
    }

    @Autowired
    public InStoreFormItemServiceImpl(InStoreFormItemRepository inStoreFormItemRepository) {
        this.inStoreFormItemRepository = inStoreFormItemRepository;
    }
}
