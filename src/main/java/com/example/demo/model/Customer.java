package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private String customerID;
    private String fName;
    private String lName;
    private String contactNo;
    private String updatedTimeStamp;
    private String createdTimeStamp;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("CustomerID")
    public String getCustomerID() {
        return customerID;
    }

    @DynamoDbAttribute("CustomerFirstName")
    public String getfName() {
        return fName;
    }

    @DynamoDbAttribute("CustomerLastName")
    public String getlName() {
        return lName;
    }

    @DynamoDbAttribute("CustomerContactNumber")
    public String getContactNo() {
        return contactNo;
    }

    @DynamoDbAttribute("CustomerCreatedTime")
    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }
}
