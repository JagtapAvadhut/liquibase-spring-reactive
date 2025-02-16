package com.reactive.liquibase.controller;

import com.reactive.liquibase.dto.CustomerDTO;
import com.reactive.liquibase.entity.Customer;
import com.reactive.liquibase.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping
    public Flux<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping
    public Mono<Customer> createCustomer(@RequestBody CustomerDTO customer) {
        return customerService.save(customer);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteById(id);
    }
}