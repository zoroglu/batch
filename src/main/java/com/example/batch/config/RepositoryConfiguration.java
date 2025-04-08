package com.example.batch.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "com.example.batch")
@EntityScan(basePackages = {"com.example.batch"})
@EnableTransactionManagement
@Configuration
public class RepositoryConfiguration {


}
