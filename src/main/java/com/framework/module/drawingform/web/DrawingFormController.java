package com.framework.module.drawingform.web;

import com.framework.module.drawingform.domain.DrawingForm;
import com.framework.module.drawingform.service.DrawingFormService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "领料单管理")
@RestController
@RequestMapping("/api/drawingForm")
public class DrawingFormController extends AbstractCrudController<DrawingForm> {
    private final DrawingFormService drawingFormService;
    @Override
    protected CrudService<DrawingForm> getService() {
        return drawingFormService;
    }

    @Autowired
    public DrawingFormController(DrawingFormService drawingFormService) {
        this.drawingFormService = drawingFormService;
    }
}
