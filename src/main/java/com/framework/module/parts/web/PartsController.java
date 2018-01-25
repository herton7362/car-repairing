package com.framework.module.parts.web;

import com.framework.module.parts.domain.Parts;
import com.framework.module.parts.service.PartsService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "配件管理")
@RestController
@RequestMapping("/api/parts")
public class PartsController extends AbstractCrudController<Parts> {
    private final PartsService partsService;
    @Override
    protected CrudService<Parts> getService() {
        return partsService;
    }

    @Autowired
    public PartsController(PartsService partsService) {
        this.partsService = partsService;
    }
}
