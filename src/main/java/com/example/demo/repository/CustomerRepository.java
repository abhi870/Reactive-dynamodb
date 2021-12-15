package com.example.demo.repository;

import com.example.demo.model.Customer;

import java.util.concurrent.CompletableFuture;

public interface CustomerRepository {
    CompletableFuture<Customer> getById(String id);

    CompletableFuture<Void> save(Customer customer);

    CompletableFuture<Customer> update(Customer customer);

    CompletableFuture<Customer> delete(String id);
}
