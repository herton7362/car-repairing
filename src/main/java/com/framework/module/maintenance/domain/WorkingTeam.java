package com.framework.module.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kratos.entity.BaseEntity;
import com.kratos.module.attachment.domain.Attachment;
import com.kratos.module.auth.domain.Admin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@ApiModel(value = "班组")
public class WorkingTeam extends BaseEntity {
    @ApiModelProperty("名称")
    @Column(length = 100)
    private String name;
    @ApiModelProperty(value = "班组人员")
    @ManyToMany
    @Where(clause="logically_deleted=0")
    @JoinTable(name="working_team_admins",joinColumns={@JoinColumn(name="working_team_id")},inverseJoinColumns={@JoinColumn(name="admin_id")})
    private List<Admin> admins;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
}
