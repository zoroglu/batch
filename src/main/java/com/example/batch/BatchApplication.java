package com.example.batch;

import com.example.batch.data.entity.Batch;
import com.example.batch.data.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BatchApplication implements CommandLineRunner {
    @Autowired
    BatchRepository batchRepository;


    public static void main(String args[]) {
        SpringApplication.run(BatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        batchRepository.saveAll(List.of(new Batch("CURRENCYRATE", 1L),
                new Batch("CURRENCYRATE", 2L)));
    }
}
