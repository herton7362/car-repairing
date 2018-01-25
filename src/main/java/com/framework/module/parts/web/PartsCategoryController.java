package com.framework.module.parts.web;

import com.framework.module.parts.domain.PartsCategory;
import com.framework.module.parts.service.PartsCategoryService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "配件类别管理")
@RestController
@RequestMapping("/api/partsCategory")
public class PartsCategoryController extends AbstractCrudController<PartsCategory> {
    private final PartsCategoryService partsCategoryService;
    @Override
    protected CrudService<PartsCategory> getService() {
        return partsCategoryService;
    }

    @Autowired
    public PartsCategoryController(PartsCategoryService partsCategoryService) {
        this.partsCategoryService = partsCategoryService;
    }
}
