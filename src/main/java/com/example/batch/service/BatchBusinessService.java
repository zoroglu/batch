package com.example.batch.service;

import com.example.batch.data.entity.Batch;
import com.example.batch.data.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchBusinessService {

    private final BatchRepository batchRepository;

    public Long findIdBatchByCode(String code) {
        Batch batch = batchRepository.findByCode(code).orElse(null);
        return batch.getId();
    }
}
