package com.framework.module.workflow.web;

import com.framework.module.shop.domain.Shop;
import com.framework.module.workflow.domain.WorkflowInstance;
import com.framework.module.workflow.service.WorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "工作流管理")
@RestController
@RequestMapping("/api/workflow")
public class WorkflowController {
    private WorkflowService workflowService;
    /**
     * 创建自定义工作流实例
     */
    @ApiOperation(value="创建自定义工作流实例")
    @RequestMapping(value = "/custom/instance", method = RequestMethod.POST)
    public ResponseEntity<?> newCustomInstance(@RequestBody CreateCustomWorkflowInstanceParam param) throws Exception {
        workflowService.newCustomInstance(param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 获取工作流实例
     */
    @ApiOperation(value="获取工作流实例")
    @RequestMapping(value = "/instance/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<WorkflowInstance> getInstance(@PathVariable String orderId) throws Exception {
        WorkflowInstance workflowInstance = workflowService.getInstance(orderId);
        return new ResponseEntity<>(workflowInstance, HttpStatus.OK);
    }

    /**
     * 审批通过
     */
    @ApiOperation(value="审批通过")
    @RequestMapping(value = "/approve/{orderId}", method = RequestMethod.POST)
    public ResponseEntity<?> approve(@PathVariable String orderId, @RequestBody ApproveParam param) throws Exception {
        workflowService.approve(orderId, param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 审批不通过
     */
    @ApiOperation(value="审批不通过")
    @RequestMapping(value = "/disapprove/{orderId}", method = RequestMethod.POST)
    public ResponseEntity<?> disapprove(@PathVariable String orderId, @RequestBody DisapproveParam param) throws Exception {
        workflowService.disapprove(orderId, param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 指定下一审批人
     */
    @ApiOperation(value="审批不通过")
    @RequestMapping(value = "/next/auditor/{orderId}", method = RequestMethod.POST)
    public ResponseEntity<?> specifyNextAuditor(@PathVariable String orderId, @RequestBody SpecifyNextAuditorParam param) throws Exception {
        workflowService.specifyNextAuditor(orderId, param);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
