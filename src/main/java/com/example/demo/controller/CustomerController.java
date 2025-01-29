package com.example.demo.controller;

import com.example.demo.dto.request.CustomerRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.dto.response.InsuranceClaimResponse;
import com.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable UUID userId) {
        CustomerResponse response = customerService.getCustomer(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/claims")
    public ResponseEntity<List<InsuranceClaimResponse>> getCustomerClaims(@PathVariable UUID userId) {
        List<InsuranceClaimResponse> response = customerService.getClaimsByCustomer(userId);
        return ResponseEntity.ok(response);
    }
}
