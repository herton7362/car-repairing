package com.framework.module.store.web;

import com.framework.module.store.domain.InStoreForm;
import com.framework.module.store.service.InStoreFormService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "入库单管理")
@RestController
@RequestMapping("/api/inStoreForm")
public class InStoreFormController extends AbstractCrudController<InStoreForm> {
    private final InStoreFormService inStoreFormService;
    @Override
    protected CrudService<InStoreForm> getService() {
        return inStoreFormService;
    }

    @Autowired
    public InStoreFormController(InStoreFormService inStoreFormService) {
        this.inStoreFormService = inStoreFormService;
    }
}
