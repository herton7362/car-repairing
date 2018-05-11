package com.framework.module.entrustform.domain;

import com.kratos.common.PageRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntrustFormRepository extends PageRepository<EntrustForm> {
    @Query("select count(o) from EntrustForm o where o.status = ?1 and o.creatorId = ?2")
    Integer countByStatusAndMemberId(EntrustForm.Status status, String creatorId);
}
