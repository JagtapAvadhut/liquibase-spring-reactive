package com.reactive.liquibase.service.impl;

import com.reactive.liquibase.dto.CustomerDTO;
import com.reactive.liquibase.entity.Customer;
import com.reactive.liquibase.exceptions.CustomerNotFoundException;
import com.reactive.liquibase.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");

        customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jane");
        customerDTO.setLastName("Doe");
        customerDTO.setEmail("jane.doe@example.com");
    }

    @Test
    void testFindAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Flux.just(customer));

        StepVerifier.create(customerService.findAll())
                .expectNext(customer)
                .verifyComplete();

        verify(customerRepo, times(1)).findAll();
    }

    @Test
    void testFindAllCustomersException() {
        when(customerRepo.findAll()).thenReturn(Flux.error(new RuntimeException("An unexpected error occurred while fetching customers")));

        StepVerifier.create(customerService.findAll())
                .expectError(RuntimeException.class)
                .verify();
        verify(customerRepo, times(1)).findAll();
    }

    @Test
    void testFindById_CustomerExists() {
        when(customerRepo.findById(1L)).thenReturn(Mono.just(customer));

        StepVerifier.create(customerService.findById(1L))
                .expectNextMatches(foundCustomer -> foundCustomer.getFirstName().equals("JOHN")) // Uppercase check
                .verifyComplete();

        verify(customerRepo, times(1)).findById(1L);
    }

    @Test
    void testFindById_CustomerNotFound() {
        when(customerRepo.findById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(customerService.findById(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(customerRepo, times(1)).findById(1L);
    }

    @Test
    void testSaveCustomer_Success() {
        when(customerRepo.existsByEmail(eq(customerDTO.getEmail()))).thenReturn(Mono.just(false));
        when(customerRepo.save(any(Customer.class))).thenReturn(Mono.just(customer));

        StepVerifier.create(customerService.save(customerDTO))
                .expectNextMatches(savedCustomer -> savedCustomer.getEmail().equals(customer.getEmail()))
                .verifyComplete();

        verify(customerRepo, times(1)).existsByEmail(eq(customerDTO.getEmail()));
        verify(customerRepo, times(1)).save(any(Customer.class));
    }

    @Test
    void testSaveCustomer_AlreadyExists() {
        when(customerRepo.existsByEmail(anyString())).thenReturn(Mono.just(true));

        StepVerifier.create(customerService.save(customerDTO))
                .expectError(CustomerNotFoundException.class)
                .verify();

        verify(customerRepo, times(1)).existsByEmail(anyString());
        verify(customerRepo, never()).save(any(Customer.class));
    }

    @Test
    void testDeleteById_CustomerExists() {
        when(customerRepo.findById(1L)).thenReturn(Mono.just(customer));
        when(customerRepo.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(customerService.deleteById(1L))
                .verifyComplete();

        verify(customerRepo, times(1)).findById(1L);
        verify(customerRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_CustomerNotFound() {
        when(customerRepo.findById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(customerService.deleteById(1L))
                .expectError(CustomerNotFoundException.class)
                .verify();

        verify(customerRepo, times(1)).findById(1L);
        verify(customerRepo, never()).deleteById(anyLong());
    }
}
