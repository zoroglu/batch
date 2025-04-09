package com.example.batch;

import com.example.batch.data.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchApplication {

    @Autowired
    BatchRepository batchRepository;

    public static void main(String args[]) {
        SpringApplication.run(BatchApplication.class, args);
    }

}

/**
 *
 * insert data for h2database
 *
 * @SpringBootApplication
 * public class BatchApplication implements CommandLineRunner {
 *
 *     public static void main(String args[]) {
 *         SpringApplication.run(BatchApplication.class, args);
 *     }
 *
 *     @Autowired
 *     BatchRepository batchRepository;
 *
 *     @Override
 *     public void run(String... args) {
 *         batchRepository.saveAll(List.of(new Batch("CURRENCYRATE", 1L),
 *                 new Batch("CURRENCYRATE", 2L)));
 *     }
 * }
 */
