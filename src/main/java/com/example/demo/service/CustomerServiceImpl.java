package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Result;
import com.example.demo.repository.CustomerRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

import static com.example.demo.model.Result.FAIL;
import static com.example.demo.model.Result.SUCCESS;
import static java.lang.String.valueOf;
import static java.time.Instant.now;


@Service
@NoArgsConstructor
@Data
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Mono<Customer> getById(String id) {
        CompletableFuture<Customer> customer = customerRepository.getById(id)
                .whenComplete((cus, ex) -> {
                    if (null == cus) {
                        log.info(ex.toString());
                        throw new IllegalArgumentException("Invalid customerId");
                    }
                }).exceptionally(ex -> new Customer());
        return Mono.fromFuture(customer);
    }

    @Override
    public Mono<Result> save(Customer customer) {
        customer.setCreatedTimeStamp(valueOf(now().getEpochSecond()));
        Result createStatus = customerRepository.save(customer)
                .handle((__, ex) -> {
                    if (ex == null) return SUCCESS;
                    else {
                        log.info(ex.toString());
                        return FAIL;
                    }
                })
                .join(); //blocked untill data is retrieved
        return Mono.just(createStatus);
    }

    //tanla platforms
    @Override
    public Mono<Result> update(Customer customer) {
        customer.setUpdatedTimeStamp(valueOf(now().getEpochSecond()));
        Result result = customerRepository.getById(customer.getCustomerID())
                .thenApply(customer1 -> {
                    if (null == customer1)
                        throw new IllegalArgumentException("Invalid Customer");
                    return customer1;
                })
                .thenCompose(__ -> customerRepository.save(customer))
                .handle((__, ex) -> ex == null ? SUCCESS : FAIL)
                .join();
        return Mono.just(result);
    }

    @Override
    public Mono<Result> delete(String id) {
        Result deleteStatus = customerRepository.delete(id)
                .handle((__, ex) -> ex == null ? SUCCESS : FAIL)
                .join();//blocked untill data is retrieved
        return Mono.just(deleteStatus);
    }
}
