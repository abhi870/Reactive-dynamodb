package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Result;
import reactor.core.publisher.Mono;


public interface CustomerService {
    Mono<Customer> getById(String id);

    Mono<Result> save(Customer customer);

    Mono<Result> update(Customer customer);

    Mono<Result> delete(String id);
}
