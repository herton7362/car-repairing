package com.framework.module.store.service;

import com.framework.module.store.domain.OutStoreForm;
import com.framework.module.store.domain.OutStoreFormItem;
import com.framework.module.store.domain.OutStoreFormRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class OutStoreFormServiceImpl extends AbstractCrudService<OutStoreForm> implements OutStoreFormService {
    private final OutStoreFormRepository outStoreFormRepository;
    @Override
    protected PageRepository<OutStoreForm> getRepository() {
        return outStoreFormRepository;
    }

    @Autowired
    public OutStoreFormServiceImpl(OutStoreFormRepository outStoreFormRepository) {
        this.outStoreFormRepository = outStoreFormRepository;
    }
}
