package com.framework.module.purchase.service;

import com.framework.module.purchase.domain.PurchaseForm;
import com.framework.module.purchase.web.InStoreParam;
import com.kratos.common.CrudService;

public interface PurchaseFormService extends CrudService<PurchaseForm> {
    /**
     * 采购入库
     * @param formId 表单id
     * @param inStoreParam 入库参数
     */
    void inStore(String formId, InStoreParam inStoreParam) throws Exception;
}
