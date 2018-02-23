package com.framework.module.store.service;

import com.framework.module.store.domain.OutStoreForm;
import com.framework.module.store.domain.OutStoreFormItem;
import com.framework.module.store.domain.OutStoreFormItemRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    protected PageRepository<OutStoreFormItem> getRepository() {
        return outStoreFormItemRepository;
    }

    @Autowired
    public OutStoreFormItemServiceImpl(OutStoreFormItemRepository outStoreFormItemRepository) {
        this.outStoreFormItemRepository = outStoreFormItemRepository;
    }
}
