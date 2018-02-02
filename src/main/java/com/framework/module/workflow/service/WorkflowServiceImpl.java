package com.framework.module.workflow.service;

import com.framework.module.workflow.domain.WorkflowInstance;
import com.framework.module.workflow.domain.WorkflowRecord;
import com.framework.module.workflow.web.ApproveParam;
import com.framework.module.workflow.web.CreateCustomWorkflowInstanceParam;
import com.framework.module.workflow.web.DisapproveParam;
import com.framework.module.workflow.web.SpecifyNextAuditorParam;
import com.kratos.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class WorkflowServiceImpl implements WorkflowService {
    private WorkflowInstanceService workflowInstanceService;
    private WorkflowRecordService workflowRecordService;
    @Override
    public void newCustomInstance(CreateCustomWorkflowInstanceParam param) throws Exception {
        if(StringUtils.isBlank(param.getAuditorId())) {
            throw new BusinessException("审批人不能为空");
        }
        if(StringUtils.isBlank(param.getOrderId())) {
            throw new BusinessException("表单id不能为空");
        }
        WorkflowInstance workflowInstance = new WorkflowInstance();
        workflowInstance.setAuditorId(param.getAuditorId());
        workflowInstance.setOrderId(param.getOrderId());
        workflowInstance.setState(WorkflowInstance.State.PENDING);
        workflowInstanceService.save(workflowInstance);
    }

    @Override
    public WorkflowInstance getInstance(String orderId) throws Exception {
        Map<String, String[]> params = new HashMap<>();
        params.put("orderId", new String[]{orderId});
        List<WorkflowInstance> workflowInstances = workflowInstanceService.findAll(params);
        if(workflowInstances != null && !workflowInstances.isEmpty()) {
            return workflowInstances.get(0);
        }
        return null;
    }

    @Override
    public void approve(String orderId, ApproveParam param) throws Exception {
        WorkflowInstance workflowInstance = getInstance(orderId);
        workflowInstance.setState(WorkflowInstance.State.APPROVE);
        workflowInstanceService.save(workflowInstance);
        record(workflowInstance);
    }

    @Override
    public void disapprove(String orderId, DisapproveParam param) throws Exception {
        WorkflowInstance workflowInstance = getInstance(orderId);
        workflowInstance.setState(WorkflowInstance.State.DISAPPROVE);
        workflowInstanceService.save(workflowInstance);
        record(workflowInstance);
    }

    @Override
    public void specifyNextAuditor(String orderId, SpecifyNextAuditorParam param) throws Exception {
        WorkflowInstance workflowInstance = getInstance(orderId);
        workflowInstance.setAuditorId(param.getAuditorId());
        workflowInstance.setState(WorkflowInstance.State.PENDING);
        workflowInstanceService.save(workflowInstance);
    }

    private void record(WorkflowInstance workflowInstance) throws Exception {
        record(workflowInstance, null);
    }

    /**
     * 记录工作流日志
     * @param workflowInstance 工作流实例
     * @param comment 审批意见
     */
    private void record(WorkflowInstance workflowInstance, String comment) throws Exception {
        WorkflowRecord workflowRecord = new WorkflowRecord();
        workflowRecord.setAuditorId(workflowInstance.getAuditorId());
        workflowRecord.setOrderId(workflowInstance.getOrderId());
        workflowRecord.setState(workflowInstance.getState());
        workflowRecord.setComment(comment);
        workflowRecordService.save(workflowRecord);
    }

    @Autowired
    public WorkflowServiceImpl(WorkflowInstanceService workflowInstanceService) {
        this.workflowInstanceService = workflowInstanceService;
    }
}
