package com.framework.module.store.service;

import com.framework.module.store.domain.OutStoreForm;
import com.framework.module.store.domain.OutStoreFormItem;
import com.framework.module.store.domain.OutStoreFormItemRepository;
import com.framework.module.store.domain.OutStoreFormRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class OutStoreFormItemServiceImpl extends AbstractCrudService<OutStoreFormItem> implements OutStoreFormItemService {
    private final OutStoreFormItemRepository outStoreFormItemRepository;
    private final StoreService storeService;
    private final OutStoreFormRepository outStoreFormRepository;
    @Override
    protected PageRepository<OutStoreFormItem> getRepository() {
        return outStoreFormItemRepository;
    }

    @Override
    public OutStoreFormItem save(OutStoreFormItem outStoreFormItem) throws Exception {
        Boolean isNew = StringUtils.isBlank(outStoreFormItem.getId());
        super.save(outStoreFormItem);
        if(isNew) {
            OutStoreForm outStoreForm = outStoreFormRepository.findOne(outStoreFormItem.getOutStoreFormId());
            storeService.outStore(outStoreForm.getStoreId(), outStoreFormItem.getRelationId(), outStoreFormItem.getCount());
        }
        return outStoreFormItem;
    }

    @Autowired
    @Lazy
    public OutStoreFormItemServiceImpl(
            OutStoreFormItemRepository outStoreFormItemRepository,
            StoreService storeService,
            OutStoreFormRepository outStoreFormRepository
    ) {
        this.outStoreFormItemRepository = outStoreFormItemRepository;
        this.storeService = storeService;
        this.outStoreFormRepository = outStoreFormRepository;
    }
}
