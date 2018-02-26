package com.framework.module.store.service;

import com.framework.module.store.domain.*;
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
public class InStoreFormServiceImpl extends AbstractCrudService<InStoreForm> implements InStoreFormService {
    private final InStoreFormRepository inStoreFormRepository;
    @Override
    protected PageRepository<InStoreForm> getRepository() {
        return inStoreFormRepository;
    }

    @Autowired
    public InStoreFormServiceImpl(InStoreFormRepository inStoreFormRepository) {
        this.inStoreFormRepository = inStoreFormRepository;
    }
}
