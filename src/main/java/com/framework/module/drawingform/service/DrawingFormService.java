package com.framework.module.drawingform.service;

import com.framework.module.drawingform.domain.DrawingForm;
import com.kratos.common.CrudService;

public interface DrawingFormService extends CrudService<DrawingForm> {
    /**
     * 出库
     * @param formId 表单id
     */
    void outStore(String formId) throws Exception;
}
