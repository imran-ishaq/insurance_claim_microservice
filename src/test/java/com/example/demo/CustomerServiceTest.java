package com.example.demo;

import com.example.demo.dto.request.CustomerRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.dto.response.InsuranceClaimResponse;
import com.example.demo.entity.Customer;
import com.example.demo.entity.InsuranceClaim;
import com.example.demo.enums.ClaimStatus;
import com.example.demo.enums.ClaimType;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerRequest customerRequest;
    private InsuranceClaim insuranceClaim;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setFullName("John Doe");
        customer.setInsuranceTypes(List.of());

        customerRequest = new CustomerRequest();
        customerRequest.setFullName("John Doe");
        customerRequest.setDateOfBirth(LocalDate.parse("1990-01-01"));
        customerRequest.setInsuranceTypes(List.of());

        insuranceClaim = new InsuranceClaim();
        insuranceClaim.setId(UUID.randomUUID());
        insuranceClaim.setClaimType(ClaimType.HEALTH);
        insuranceClaim.setStatus(ClaimStatus.PENDING);
        insuranceClaim.setCost(500.0);
        insuranceClaim.setUserId(customer.getId());
    }

    @Test
    void testCreateCustomer_Success() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponse response = customerService.createCustomer(customerRequest);

        assertNotNull(response);
        assertEquals(customer.getFullName(), response.getFullName());
        assertEquals(customer.getId(), response.getId());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testGetCustomer_Success() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.getCustomer(customer.getId());

        assertNotNull(response);
        assertEquals(customer.getFullName(), response.getFullName());
        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    void testGetCustomer_CustomerNotFound() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomer(customer.getId());
        });

        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    void testGetClaimsByCustomer_Success() {
        customer.setInsuranceClaims(Set.of(insuranceClaim));
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        List<InsuranceClaimResponse> responses = customerService.getClaimsByCustomer(customer.getId());

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(insuranceClaim.getId(), responses.get(0).getId());
        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    void testGetClaimsByCustomer_CustomerNotFound() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getClaimsByCustomer(customer.getId());
        });

        verify(customerRepository, times(1)).findById(customer.getId());
    }
}
