package com.framework.module.drawingform.service;

import com.framework.module.drawingform.domain.DrawingForm;
import com.framework.module.drawingform.web.OutStoreParam;
import com.kratos.common.CrudService;

public interface DrawingFormService extends CrudService<DrawingForm> {
    /**
     * 出库
     * @param formId 表单id
     * @param outStoreParam 领料出库参数
     */
    void outStore(String formId, OutStoreParam outStoreParam) throws Exception;
}
