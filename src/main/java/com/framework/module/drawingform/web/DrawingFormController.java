package com.framework.module.drawingform.web;

import com.framework.module.drawingform.domain.DrawingForm;
import com.framework.module.drawingform.service.DrawingFormService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "领料单管理")
@RestController
@RequestMapping("/api/drawingForm")
public class DrawingFormController extends AbstractCrudController<DrawingForm> {
    private final DrawingFormService drawingFormService;
    @Override
    protected CrudService<DrawingForm> getService() {
        return drawingFormService;
    }

    /**
     * 领料出库
     */
    @ApiOperation(value="领料出库")
    @RequestMapping(value = "/outStore/{formId}", method = RequestMethod.POST)
    public ResponseEntity<?> outStore(@PathVariable String formId, @RequestBody OutStoreParam outStoreParam) throws Exception {
        drawingFormService.outStore(formId, outStoreParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public DrawingFormController(DrawingFormService drawingFormService) {
        this.drawingFormService = drawingFormService;
    }
}
