package com.reactive.liquibase.service;


import com.reactive.liquibase.dto.CustomerDTO;
import com.reactive.liquibase.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<Customer> findAll();

    Mono<Customer> findById(Long id);

    Mono<Customer> save(CustomerDTO customer);

    Mono<Void> deleteById(Long id);
}
