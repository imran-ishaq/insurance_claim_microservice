package com.example.demo.service;

import com.example.demo.dto.request.CustomerRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.dto.response.InsuranceClaimResponse;
import com.example.demo.entity.Customer;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.mapper.InsuranceClaimMapper;
import com.example.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest request) {
        return CustomerMapper.toResponse(customerRepository.save(CustomerMapper.toEntity(request)));
    }

    public CustomerResponse getCustomer(UUID userId) {
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return CustomerMapper.toResponse(customer);
    }

    public List<InsuranceClaimResponse> getClaimsByCustomer(UUID userId) {
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        return customer.getInsuranceClaims()
                .stream()
                .map(InsuranceClaimMapper::toResponse)
                .collect(Collectors.toList());
    }
}
