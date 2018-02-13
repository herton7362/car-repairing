package com.framework.module.dispatchform.service;

import com.framework.module.dispatchform.domain.DispatchForm;
import com.kratos.common.CrudService;

public interface DispatchFormService extends CrudService<DispatchForm> {
    /**
     * 领料
     * @param formId 派工单id
     */
    void draw(String formId) throws Exception;
}
