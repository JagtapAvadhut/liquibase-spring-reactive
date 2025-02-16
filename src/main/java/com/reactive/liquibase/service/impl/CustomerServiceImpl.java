package com.reactive.liquibase.service.impl;

import com.reactive.liquibase.dto.CustomerDTO;
import com.reactive.liquibase.entity.Customer;
import com.reactive.liquibase.repository.CustomerRepo;
import com.reactive.liquibase.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;


    @Override
    public Flux<Customer> findAll() {
        return customerRepo.findAll();
    }

    @Override
    public Mono<Customer> findById(Long id) {
        return customerRepo.findById(id);
    }

    @Override
    public Mono<Customer> save(CustomerDTO customer) {
        Customer customer1 = new Customer();
        customer1.setEmail(customer.getEmail());
        customer1.setFirstName(customer.getFirstName());
        customer1.setLastName(customer.getLastName());
        return customerRepo.save(customer1);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return customerRepo.deleteById(id);
    }
}