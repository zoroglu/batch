package com.example.batch.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "BATCH")
@SequenceGenerator(name = "BatchSeq", sequenceName = "BATCH_S", allocationSize = 1)
public class Batch extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BatchSeq")
    private Long id;

    @Column(name = "CODE")
    private String code;

}