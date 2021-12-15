package com.example.demo.repository;

import com.example.demo.model.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;


@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    @Autowired
    private DynamoDbEnhancedAsyncClient getDynamoDbEnhancedAsyncClient;
    private DynamoDbAsyncTable<Customer> customerDynamoDbAsyncTable;

    @PostConstruct
    public void setup() {
        this.customerDynamoDbAsyncTable = getDynamoDbEnhancedAsyncClient
                .table(Customer.class.getSimpleName(), TableSchema.fromBean(Customer.class));
    }

    @Override
    public CompletableFuture<Customer> getById(String id) {
        return customerDynamoDbAsyncTable.getItem(getKeyBuild(id));
    }

    @Override
    public CompletableFuture<Void> save(Customer customer) {
        return customerDynamoDbAsyncTable.putItem(customer);
    }

    @Override
    public CompletableFuture<Customer> update(Customer customer) {
        return customerDynamoDbAsyncTable.updateItem(customer);
    }

    @Override
    public CompletableFuture<Customer> delete(String id) {
        return customerDynamoDbAsyncTable.deleteItem(getKeyBuild(id));
    }

    private Key getKeyBuild(String customerId) {
        return Key.builder().partitionValue(customerId).build();
    }
}
