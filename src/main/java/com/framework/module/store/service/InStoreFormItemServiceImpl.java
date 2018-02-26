package com.framework.module.store.service;

import com.framework.module.store.domain.InStoreForm;
import com.framework.module.store.domain.InStoreFormItem;
import com.framework.module.store.domain.InStoreFormItemRepository;
import com.framework.module.store.domain.InStoreFormRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class InStoreFormItemServiceImpl extends AbstractCrudService<InStoreFormItem> implements InStoreFormItemService {
    private final InStoreFormItemRepository inStoreFormItemRepository;
    private final StoreService storeService;
    private final InStoreFormRepository inStoreFormRepository;
    @Override
    protected PageRepository<InStoreFormItem> getRepository() {
        return inStoreFormItemRepository;
    }

    @Override
    public InStoreFormItem save(InStoreFormItem inStoreFormItem) throws Exception {
        Boolean isNew = StringUtils.isBlank(inStoreFormItem.getId());
        InStoreForm inStoreForm = inStoreFormRepository.findOne(inStoreFormItem.getInStoreFormId());
        if(isNew) {
            storeService.inStore(inStoreForm.getStoreId(), inStoreFormItem.getRelationId(), inStoreFormItem.getCount());
        }
        return inStoreFormItem;
    }

    @Autowired
    @Lazy
    public InStoreFormItemServiceImpl(
            InStoreFormItemRepository inStoreFormItemRepository,
            StoreService storeService,
            InStoreFormRepository inStoreFormRepository
    ) {
        this.inStoreFormItemRepository = inStoreFormItemRepository;
        this.storeService = storeService;
        this.inStoreFormRepository = inStoreFormRepository;
    }
}
