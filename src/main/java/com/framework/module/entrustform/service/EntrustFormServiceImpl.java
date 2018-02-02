package com.framework.module.entrustform.service;

import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.domain.EntrustFormRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class EntrustFormServiceImpl extends AbstractCrudService<EntrustForm> implements EntrustFormService {
    private final EntrustFormRepository entrustFormRepository;
    @Override
    protected PageRepository<EntrustForm> getRepository() {
        return entrustFormRepository;
    }

    @Autowired
    public EntrustFormServiceImpl(EntrustFormRepository entrustFormRepository) {
        this.entrustFormRepository = entrustFormRepository;
    }
}
