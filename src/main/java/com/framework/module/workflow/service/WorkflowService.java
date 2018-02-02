package com.framework.module.workflow.service;

import com.framework.module.workflow.domain.WorkflowInstance;
import com.framework.module.workflow.web.ApproveParam;
import com.framework.module.workflow.web.CreateCustomWorkflowInstanceParam;
import com.framework.module.workflow.web.DisapproveParam;
import com.framework.module.workflow.web.SpecifyNextAuditorParam;

public interface WorkflowService {
    /**
     * 创建自定义工作流实例
     * @param param 参数 {@link CreateCustomWorkflowInstanceParam}
     */
    void newCustomInstance(CreateCustomWorkflowInstanceParam param) throws Exception;

    /**
     * 获取工作流实例
     * @param orderId 表单id
     * @return 工作流实例
     */
    WorkflowInstance getInstance(String orderId) throws Exception;

    /**
     * 审核通过
     * @param orderId 表单id
     * @param param 参数 {@link ApproveParam}
     */
    void approve(String orderId, ApproveParam param) throws Exception;

    /**
     * 审核不通过
     * @param orderId 表单id
     * @param param 参数 {@link DisapproveParam}
     */
    void disapprove(String orderId, DisapproveParam param) throws Exception;

    /**
     * 指定下一审批人
     * @param orderId 表单id
     * @param param 参数 {@link SpecifyNextAuditorParam}
     */
    void specifyNextAuditor(String orderId, SpecifyNextAuditorParam param) throws Exception;
}
