package com.example.batch.rest;

import com.example.batch.service.BatchBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("batch")
public class BatchService {

    private final BatchBusinessService batchBusinessService;

    @GetMapping(value = {"/findIdBatchByCode"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Long> findIdBatchByCode(@RequestParam("code") String code) {
        return ResponseEntity.ok(batchBusinessService.findIdBatchByCode(code));

    };

}
