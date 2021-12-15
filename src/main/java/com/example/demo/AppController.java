package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.model.Result;
import com.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class AppController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get/{id}")
    public Mono<Customer> getCustomer(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        return customerService.getById(id);
    }

    @PostMapping("/save")
    public Mono<Result> saveCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/update")
    public Mono<Result> updateCustomer(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Result> deleteCustomer(@PathVariable("id") String id) {
        return customerService.delete(id);
    }
}
