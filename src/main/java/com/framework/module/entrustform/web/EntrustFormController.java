package com.framework.module.entrustform.web;

import com.framework.module.entrustform.domain.EntrustForm;
import com.framework.module.entrustform.service.EntrustFormService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "委托书管理")
@RestController
@RequestMapping("/api/entrustForm")
public class EntrustFormController extends AbstractCrudController<EntrustForm> {
    private final EntrustFormService entrustFormService;
    @Override
    protected CrudService<EntrustForm> getService() {
        return entrustFormService;
    }

    /**
     * 生成派工单
     */
    @ApiOperation(value="生成派工单")
    @RequestMapping(value = "/dispatch/{formId}", method = RequestMethod.POST)
    public ResponseEntity<?> dispatch(@PathVariable String formId, @RequestBody DispatchParam dispatchParam) throws Exception {
        entrustFormService.dispatch(formId, dispatchParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Autowired
    public EntrustFormController(EntrustFormService entrustFormService) {
        this.entrustFormService = entrustFormService;
    }
}
