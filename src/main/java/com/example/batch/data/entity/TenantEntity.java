package com.example.batch.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@FilterDef(name = "companyFilter",
        parameters = @ParamDef(name = "companyId", type = Long.class),
        defaultCondition = "companyId" + "= :" + "companyId")
@Filter(name = "companyFilter")
public abstract class TenantEntity extends BaseEntity {

    @Column(name = "COMPANYID")
    protected Long companyId;

    public TenantEntity() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}
