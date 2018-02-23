package com.framework.module.purchase.web;

import com.framework.module.purchase.domain.PurchaseForm;
import com.framework.module.purchase.service.PurchaseFormService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "采购单管理")
@RestController
@RequestMapping("/api/purchaseForm")
public class PurchaseFormController extends AbstractCrudController<PurchaseForm> {
    private final PurchaseFormService purchaseFormService;
    @Override
    protected CrudService<PurchaseForm> getService() {
        return purchaseFormService;
    }

    @Autowired
    public PurchaseFormController(PurchaseFormService purchaseFormService) {
        this.purchaseFormService = purchaseFormService;
    }
}
