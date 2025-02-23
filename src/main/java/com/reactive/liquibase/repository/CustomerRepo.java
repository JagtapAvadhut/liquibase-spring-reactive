package com.reactive.liquibase.repository;

import com.reactive.liquibase.entity.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepo extends R2dbcRepository<Customer, Long> {
    Mono<Boolean> existsByEmail(String email);
}