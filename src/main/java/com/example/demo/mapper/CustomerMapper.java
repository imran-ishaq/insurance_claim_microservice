package com.example.demo.mapper;

import com.example.demo.dto.request.CustomerRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .fullName(request.getFullName())
                .dateOfBirth(request.getDateOfBirth())
                .insuranceTypes(request.getInsuranceTypes())
                .build();
    }

    public static CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .dateOfBirth(customer.getDateOfBirth())
                .insuranceTypes(customer.getInsuranceTypes())
                .insuranceClaim(customer.getInsuranceClaims())
                .build();
    }
}
