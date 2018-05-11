package com.framework.module.parts.service;

import com.framework.module.parts.domain.PartsImage;
import com.framework.module.parts.domain.PartsImageRepository;
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
public class PartsImageServiceImpl extends AbstractCrudService<PartsImage> implements PartsImageService {
    private final PartsImageRepository partsImageRepository;
    @Override
    protected PageRepository<PartsImage> getRepository() {
        return partsImageRepository;
    }

    @Override
    public List<PartsImage> findAll(Map<String, String[]> param) throws Exception {
        return partsImageRepository.findAll(this.getSpecificationForAllEntities(param));
    }

    @Override
    public PageResult<PartsImage> findAll(PageRequest pageRequest, Map<String, String[]> param) throws Exception {
        Page<PartsImage> page = partsImageRepository.findAll(this.getSpecificationForAllEntities(param), pageRequest);
        PageResult<PartsImage> pageResult = new PageResult<>();
        pageResult.setSize(page.getSize());
        pageResult.setTotalElements(page.getTotalElements());
        pageResult.setContent(page.getContent());
        return pageResult;
    }

    @Autowired
    public PartsImageServiceImpl(PartsImageRepository partsImageRepository) {
        this.partsImageRepository = partsImageRepository;
    }
}
