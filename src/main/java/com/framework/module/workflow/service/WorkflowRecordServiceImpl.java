package com.framework.module.workflow.service;

import com.framework.module.workflow.domain.WorkflowRecord;
import com.framework.module.workflow.domain.WorkflowRecordReopsitory;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class WorkflowRecordServiceImpl extends AbstractCrudService<WorkflowRecord> implements WorkflowRecordService {
    private final WorkflowRecordReopsitory workflowRecordReopsitory;
    @Override
    protected PageRepository<WorkflowRecord> getRepository() {
        return workflowRecordReopsitory;
    }

    @Autowired
    public WorkflowRecordServiceImpl(WorkflowRecordReopsitory workflowRecordReopsitory) {
        this.workflowRecordReopsitory = workflowRecordReopsitory;
    }
}
