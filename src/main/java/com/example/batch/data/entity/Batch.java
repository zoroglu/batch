package com.example.batch.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.io.Serializable;

@Data
@Entity
@Table(name = "BATCHTEMP")
@SequenceGenerator(name = "BatchTempSeq", sequenceName = "BATCHTEMP_S", allocationSize = 1)
@FilterDef(name = "companyFilter",
        parameters = @ParamDef(name = "companyId", type = Long.class),
        defaultCondition = "companyId" + "= :" + "companyId")
@Filter(name = "companyFilter")
public class Batch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BatchTempSeq")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "COMPANYID")
    protected Long companyId;

    @Version
    @Column(name = "VERSIONID")
    protected Long versionId;

}