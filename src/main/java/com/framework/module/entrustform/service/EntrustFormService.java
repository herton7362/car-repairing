package com.framework.module.entrustform.service;

import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.web.DispatchParam;
import com.framework.module.entrustform.web.PayParam;
import com.kratos.common.CrudService;

public interface EntrustFormService extends CrudService<EntrustForm> {
    /**
     * 派工
     * @param formId 委托单id
     * @param dispatchParam 参数 {@link DispatchParam}
     */
    void dispatch(String formId, DispatchParam dispatchParam) throws Exception;

    /**
     * 收款
     * @param formId 委托单id
     * @param payParam 参数
     */
    void pay(String formId, PayParam payParam) throws Exception;

    /**
     * 竣工
     * @param formId 委托单id
     */
    void finish(String formId) throws Exception;
}
