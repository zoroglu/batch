package com.example.batch.data.repository;

import com.example.batch.data.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long>,
        JpaSpecificationExecutor<Batch> {

    Optional<Batch> findById(Long id);

    Optional<Batch> findByCode(String code);

}
