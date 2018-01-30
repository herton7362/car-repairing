package com.framework.module.maintenance.web;

import com.framework.module.maintenance.domain.WorkingTeam;
import com.framework.module.maintenance.service.WorkingTeamService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "班组管理")
@RestController
@RequestMapping("/api/workingTeam")
public class WorkingTeamServiceController extends AbstractCrudController<WorkingTeam> {
    private final WorkingTeamService workingTeamService;
    @Override
    protected CrudService<WorkingTeam> getService() {
        return workingTeamService;
    }

    @Autowired
    public WorkingTeamServiceController(WorkingTeamService workingTeamService) {
        this.workingTeamService = workingTeamService;
    }
}
