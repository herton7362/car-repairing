package com.framework.module.parts.service;

import com.framework.module.parts.domain.Parts;
import com.framework.module.parts.domain.PartsCategory;
import com.framework.module.parts.domain.PartsCategoryRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@Transactional
public class PartsCategoryServiceImpl extends AbstractCrudService<PartsCategory> implements PartsCategoryService {
    private final PartsCategoryRepository partsCategoryRepository;
    private final PartsService partsService;

    @Override
    protected PageRepository<PartsCategory> getRepository() {
        return partsCategoryRepository;
    }

    @Override
    public List<PartsCategory> findAll(Map<String, String[]> param) throws Exception {
        return partsCategoryRepository.findAll(this.getSpecificationForAllEntities(param));
    }

    @Override
    public PageResult<PartsCategory> findAll(PageRequest pageRequest, Map<String, String[]> param) throws Exception {
        Page<PartsCategory> page = partsCategoryRepository.findAll(this.getSpecificationForAllEntities(param), pageRequest);
        PageResult<PartsCategory> pageResult = new PageResult<>();
        pageResult.setSize(page.getSize());
        pageResult.setTotalElements(page.getTotalElements());
        pageResult.setContent(page.getContent());
        return pageResult;
    }

    @Override
    public void delete(String id) throws Exception {
        PartsCategory partsCategory = partsCategoryRepository.findOne(id);
        List<Parts> parts = partsCategory.getParts();
        for (Parts part : parts) {
            part.setLogicallyDeleted(true);
            partsService.save(part);
        }
        super.delete(id);
    }

    @Autowired
    public PartsCategoryServiceImpl(
            PartsCategoryRepository partsCategoryRepository,
            PartsService partsService
    ) {
        this.partsCategoryRepository = partsCategoryRepository;
        this.partsService = partsService;
    }
}
