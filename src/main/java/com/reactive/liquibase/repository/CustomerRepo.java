package com.reactive.liquibase.repository;

import com.reactive.liquibase.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends ReactiveCrudRepository<Customer, Long> {
}