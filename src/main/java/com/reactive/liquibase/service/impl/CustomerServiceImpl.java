package com.reactive.liquibase.service.impl;

import com.reactive.liquibase.dto.CustomerDTO;
import com.reactive.liquibase.entity.Customer;
import com.reactive.liquibase.exceptions.CustomerNotFoundException;
import com.reactive.liquibase.exceptions.ErrorCode;
import com.reactive.liquibase.repository.CustomerRepo;
import com.reactive.liquibase.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    @Override
    public Flux<Customer> findAll() {
        return customerRepo.findAll()
                .doOnNext(customer -> log.info("Retrieved customer: {}", customer))
                .doOnError(error -> log.error("Error fetching customers", error))
                .onErrorResume(error -> {
                    log.error("Unexpected error in findAll(): {}", error.getMessage());
                    return Flux.error(new RuntimeException("An unexpected error occurred while fetching customers"));
                });
    }

    @Override
    public Mono<Customer> findById(Long id) {
        return customerRepo.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(id, ErrorCode.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND)))
                .map(customer -> {
                    customer.setFirstName(customer.getFirstName().toUpperCase());
                    return customer;
                })
                .doOnSuccess(customer -> log.info("Fetched Customer: {}", customer))
                .doOnError(error -> log.error("Error fetching customer with ID: {}", id, error))
                .onErrorResume(error -> {
                    log.error("Unexpected error in findById(): {}", error.getMessage());
                    return Mono.error(new RuntimeException("An unexpected error occurred while fetching the customer"));
                });
    }

    @Override
    @Transactional
    public Mono<Customer> save(CustomerDTO customerDTO) {
        return customerRepo.existsByEmail(customerDTO.getEmail())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        log.error("Customer already exists with email: {}", customerDTO.getEmail());
                        return CustomerNotFoundException.error(customerDTO.getEmail(), ErrorCode.CUSTOMER_ALREADY_EXISTS);
                    } else if (Boolean.FALSE.equals(exists)) {
                        return Mono.error(new RuntimeException("Customer already exists with email: " + customerDTO));
                    }

                    Customer customer = new Customer();
                    customer.setEmail(customerDTO.getEmail());
                    customer.setFirstName(customerDTO.getFirstName());
                    customer.setLastName(customerDTO.getLastName());

                    return customerRepo.save(customer)
                            .doOnSuccess(savedCustomer -> log.info("Customer saved successfully: {}", savedCustomer))
                            .doOnError(error -> log.error("Error saving customer", error));
                })
                .onErrorResume(error -> {
                    log.error("Unexpected error in save(): {}", error.getMessage());
                    return error instanceof CustomerNotFoundException
                            ? Mono.error(error)
                            : CustomerNotFoundException.error(customerDTO.getEmail(), ErrorCode.CUSTOMER_INTERNAL_ERROR);
                });
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(Long id) {
        return customerRepo.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(id, ErrorCode.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND)))
                .flatMap(customer -> customerRepo.deleteById(id)
                        .doOnSuccess(v -> log.info("Deleted customer with ID: {}", id))
                        .doOnError(error -> log.error("Error deleting customer with ID: {}", id, error))
                )
                .onErrorResume(error -> {
                    log.error("Unexpected error in deleteById(): {}", error.getMessage());
                    return CustomerNotFoundException.error(id, ErrorCode.CUSTOMER_INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }
}