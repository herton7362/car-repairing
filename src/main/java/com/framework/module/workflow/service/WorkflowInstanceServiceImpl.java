package com.framework.module.workflow.service;

import com.framework.module.workflow.domain.WorkflowInstance;
import com.framework.module.workflow.domain.WorkflowInstanceRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class WorkflowInstanceServiceImpl extends AbstractCrudService<WorkflowInstance> implements WorkflowInstanceService {
    private final WorkflowInstanceRepository workflowInstanceRepository;
    @Override
    protected PageRepository<WorkflowInstance> getRepository() {
        return workflowInstanceRepository;
    }

    @Autowired
    public WorkflowInstanceServiceImpl(WorkflowInstanceRepository workflowInstanceRepository) {
        this.workflowInstanceRepository = workflowInstanceRepository;
    }
}
