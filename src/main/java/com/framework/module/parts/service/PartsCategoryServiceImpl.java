package com.framework.module.parts.service;

import com.framework.module.parts.domain.PartsCategory;
import com.framework.module.parts.domain.PartsCategoryRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PartsCategoryServiceImpl extends AbstractCrudService<PartsCategory> implements PartsCategoryService {
    private final PartsCategoryRepository partsCategoryRepository;
    @Override
    protected PageRepository<PartsCategory> getRepository() {
        return partsCategoryRepository;
    }

    @Autowired
    public PartsCategoryServiceImpl(
            PartsCategoryRepository partsCategoryRepository
    ) {
        this.partsCategoryRepository = partsCategoryRepository;
    }
}
