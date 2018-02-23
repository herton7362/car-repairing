package com.framework.module.drawingform.service;

import com.framework.module.drawingform.domain.DrawingForm;
import com.framework.module.drawingform.domain.DrawingFormRepository;
import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.domain.EntrustFormItem;
import com.framework.module.entrustform.domain.EntrustFormItemRepository;
import com.framework.module.entrustform.domain.EntrustFormParts;
import com.framework.module.entrustform.service.EntrustFormService;
import com.framework.module.store.domain.OutStoreForm;
import com.framework.module.store.domain.OutStoreFormItem;
import com.framework.module.store.service.OutStoreFormItemService;
import com.framework.module.store.service.OutStoreFormService;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional
public class DrawingFormServiceImpl extends AbstractCrudService<DrawingForm> implements DrawingFormService {
    private final DrawingFormRepository drawingFormRepository;
    private final OutStoreFormService outStoreFormService;
    private final OutStoreFormItemService outStoreFormItemService;
    private final EntrustFormService entrustFormService;
    @Override
    protected PageRepository<DrawingForm> getRepository() {
        return drawingFormRepository;
    }

    @Override
    public void outStore(String formId) throws Exception {
        DrawingForm drawingForm = drawingFormRepository.findOne(formId);
        drawingForm.setStatus(DrawingForm.Status.OUT_STORE);
        drawingFormRepository.save(drawingForm);
        EntrustForm entrustForm = entrustFormService.findOne(drawingForm.getEntrustFormId());
        List<EntrustFormParts> partses = entrustForm.getPartses();
        OutStoreForm outStoreForm = new OutStoreForm();
        outStoreForm.setOutStoreDate(new Date());
        outStoreForm.setBusinessType(OutStoreForm.BusinessType.DRAWING);
        outStoreForm = outStoreFormService.save(outStoreForm);
        for (EntrustFormParts parts : partses) {
            OutStoreFormItem outStoreFormItem = new OutStoreFormItem();
            outStoreFormItem.setCount(parts.getCount());
            outStoreFormItem.setRelationId(parts.getParts().getId());
            outStoreFormItem.setOutStoreFormId(outStoreForm.getId());
            outStoreFormItemService.save(outStoreFormItem);
        }
    }

    @Autowired
    @Lazy
    public DrawingFormServiceImpl(
            DrawingFormRepository drawingFormRepository,
            OutStoreFormService outStoreFormService,
            EntrustFormService entrustFormService,
            OutStoreFormItemService outStoreFormItemService
    ) {
        this.drawingFormRepository = drawingFormRepository;
        this.outStoreFormService = outStoreFormService;
        this.entrustFormService = entrustFormService;
        this.outStoreFormItemService = outStoreFormItemService;
    }
}
