package com.framework.module.drawingform.service;

import com.framework.module.drawingform.domain.DrawingForm;
import com.framework.module.drawingform.domain.DrawingFormRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DrawingFormServiceImpl extends AbstractCrudService<DrawingForm> implements DrawingFormService {
    private final DrawingFormRepository drawingFormRepository;
    @Override
    protected PageRepository<DrawingForm> getRepository() {
        return drawingFormRepository;
    }

    @Autowired
    public DrawingFormServiceImpl(DrawingFormRepository drawingFormRepository) {
        this.drawingFormRepository = drawingFormRepository;
    }
}
