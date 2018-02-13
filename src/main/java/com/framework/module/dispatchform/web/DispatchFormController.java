package com.framework.module.dispatchform.web;

import com.framework.module.dispatchform.domain.DispatchForm;
import com.framework.module.dispatchform.service.DispatchFormService;
import com.framework.module.drawingform.service.DrawingFormService;
import com.framework.module.entrustform.web.DispatchParam;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "派工单管理")
@RestController
@RequestMapping("/api/dispatchForm")
public class DispatchFormController extends AbstractCrudController<DispatchForm> {
    private final DispatchFormService dispatchFormService;
    @Override
    protected CrudService<DispatchForm> getService() {
        return dispatchFormService;
    }

    /**
     * 生成领料单
     */
    @ApiOperation(value="生成领料单")
    @RequestMapping(value = "/draw/{formId}", method = RequestMethod.POST)
    public ResponseEntity<?> draw(@PathVariable String formId) throws Exception {
        dispatchFormService.draw(formId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public DispatchFormController(
            DispatchFormService dispatchFormService
    ) {
        this.dispatchFormService = dispatchFormService;
    }
}
