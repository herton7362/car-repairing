package com.framework.module.parts.service;

import com.framework.module.parts.domain.Parts;
import com.framework.module.parts.domain.PartsRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.CrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PartsServiceImpl extends AbstractCrudService<Parts> implements PartsService {
    private final PartsRepository partsRepository;
    @Override
    protected PageRepository<Parts> getRepository() {
        return partsRepository;
    }

    @Autowired
    public PartsServiceImpl (PartsRepository partsRepository) {
        this.partsRepository = partsRepository;
    }
}
