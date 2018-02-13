package com.framework.module.store.web;

import com.framework.module.store.domain.OutStoreForm;
import com.framework.module.store.service.OutStoreFormService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "出库单管理")
@RestController
@RequestMapping("/api/outStoreForm")
public class OutStoreFormController extends AbstractCrudController<OutStoreForm> {
    private final OutStoreFormService outStoreFormService;
    @Override
    protected CrudService<OutStoreForm> getService() {
        return outStoreFormService;
    }

    @Autowired
    public OutStoreFormController(OutStoreFormService outStoreFormService) {
        this.outStoreFormService = outStoreFormService;
    }
}
