package com.framework.module.purchase.web;

import com.framework.module.purchase.domain.PurchaseForm;
import com.framework.module.purchase.service.PurchaseFormService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "采购单管理")
@RestController
@RequestMapping("/api/purchaseForm")
public class PurchaseFormController extends AbstractCrudController<PurchaseForm> {
    private final PurchaseFormService purchaseFormService;
    @Override
    protected CrudService<PurchaseForm> getService() {
        return purchaseFormService;
    }

    /**
     * 采购入库
     */
    @ApiOperation(value="采购入库")
    @RequestMapping(value = "/inStore/{formId}", method = RequestMethod.POST)
    public ResponseEntity<?> inStore(@PathVariable String formId, @RequestBody InStoreParam inStoreParam) throws Exception {
        purchaseFormService.inStore(formId, inStoreParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public PurchaseFormController(PurchaseFormService purchaseFormService) {
        this.purchaseFormService = purchaseFormService;
    }
}
