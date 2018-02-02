package com.framework.module.entrustform.web;

import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.service.EntrustFormService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "委托书管理")
@RestController
@RequestMapping("/api/entrustForm")
public class EntrustFormController extends AbstractCrudController<EntrustForm> {
    private final EntrustFormService entrustFormService;
    @Override
    protected CrudService<EntrustForm> getService() {
        return entrustFormService;
    }

    @Autowired
    public EntrustFormController(EntrustFormService entrustFormService) {
        this.entrustFormService = entrustFormService;
    }
}
