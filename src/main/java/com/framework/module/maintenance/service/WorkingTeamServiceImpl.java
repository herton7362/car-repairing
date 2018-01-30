package com.framework.module.maintenance.service;

import com.framework.module.maintenance.domain.WorkingTeam;
import com.framework.module.maintenance.domain.WorkingTeamRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class WorkingTeamServiceImpl extends AbstractCrudService<WorkingTeam> implements WorkingTeamService {
    private final WorkingTeamRepository workingTeamRepository;
    @Override
    protected PageRepository<WorkingTeam> getRepository() {
        return workingTeamRepository;
    }

    @Autowired
    public WorkingTeamServiceImpl(WorkingTeamRepository workingTeamRepository) {
        this.workingTeamRepository = workingTeamRepository;
    }
}
