package com.framework.module.dispatchform.service;

import com.framework.module.dispatchform.domain.DispatchForm;
import com.framework.module.dispatchform.domain.DispatchFormRepository;
import com.framework.module.drawingform.domain.DrawingForm;
import com.framework.module.drawingform.service.DrawingFormService;
import com.framework.module.orderform.service.OrderFormService;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DispatchFormServiceImpl extends AbstractCrudService<DispatchForm> implements DispatchFormService {
    private final DispatchFormRepository dispatchFormRepository;
    private final OrderFormService orderFormService;
    private final DrawingFormService drawingFormService;
    @Override
    protected PageRepository<DispatchForm> getRepository() {
        return dispatchFormRepository;
    }

    @Override
    public void draw(String formId) throws Exception {
        if(StringUtils.isBlank(formId)) {
            throw new BusinessException("派工单id不能为空");
        }
        DispatchForm dispatchForm = dispatchFormRepository.findOne(formId);
        if(dispatchForm == null) {
            throw new BusinessException("派工单未找到");
        }
        dispatchForm.setStatus(DispatchForm.Status.DRAWING);
        dispatchFormRepository.save(dispatchForm);
        DrawingForm drawingForm = new DrawingForm();
        drawingForm.setEntrustFormId(dispatchForm.getEntrustFormId());
        drawingForm.setEntrustFormOrderNumber(dispatchForm.getEntrustFormOrderNumber());
        drawingForm.setOrderNumber(orderFormService.getOutTradeNo());
        drawingForm.setStatus(DrawingForm.Status.NEW);
        drawingForm.setWorkerId(dispatchForm.getWorkerId());
        drawingForm.setWorkingTeamId(dispatchForm.getWorkingTeamId());
        drawingFormService.save(drawingForm);
    }

    @Autowired
    public DispatchFormServiceImpl(
            DispatchFormRepository dispatchFormRepository,
            OrderFormService orderFormService,
            DrawingFormService drawingFormService
    ) {
        this.dispatchFormRepository = dispatchFormRepository;
        this.orderFormService = orderFormService;
        this.drawingFormService = drawingFormService;
    }
}
