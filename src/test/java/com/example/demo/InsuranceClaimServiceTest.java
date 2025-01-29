package com.example.demo;

import com.example.demo.dto.request.InsuranceClaimRequest;
import com.example.demo.dto.response.InsuranceClaimResponse;
import com.example.demo.entity.Customer;
import com.example.demo.entity.InsuranceClaim;
import com.example.demo.enums.ClaimStatus;
import com.example.demo.enums.ClaimType;
import com.example.demo.exception.ClaimNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.InsuranceClaimRepository;
import com.example.demo.service.InsuranceClaimService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InsuranceClaimServiceTest {

    @Mock
    private InsuranceClaimRepository claimRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private InsuranceClaimService insuranceClaimService;

    private Customer customer;
    private InsuranceClaimRequest claimRequest;
    private InsuranceClaim claim;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setInsuranceTypes(List.of(ClaimType.HEALTH, ClaimType.CAR));

        claimRequest = InsuranceClaimRequest.builder()
                .userId(customer.getId())
                .claimType(ClaimType.HEALTH)
                .cost(500.0)
                .build();

        claim = new InsuranceClaim();
        claim.setId(UUID.randomUUID());
        claim.setClaimType(ClaimType.HEALTH);
        claim.setStatus(ClaimStatus.PENDING);
        claim.setCost(500.0);
        claim.setUserId(customer.getId());
    }

    @Test
    void testCreateClaim_Success() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(claimRepository.save(any(InsuranceClaim.class))).thenReturn(claim);

        InsuranceClaimResponse response = insuranceClaimService.createClaim(claimRequest);

        assertNotNull(response);
        assertEquals(claim.getId(), response.getId());
        verify(customerRepository, times(1)).findById(customer.getId());
        verify(claimRepository, times(1)).save(any(InsuranceClaim.class));
    }

    @Test
    void testCreateClaim_CustomerNotFound() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            insuranceClaimService.createClaim(claimRequest);
        });

        verify(customerRepository, times(1)).findById(customer.getId());
        verify(claimRepository, times(0)).save(any(InsuranceClaim.class));
    }

    @Test
    void testCreateClaim_InvalidInsuranceType() {
        customer.setInsuranceTypes(List.of(ClaimType.CAR));

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            insuranceClaimService.createClaim(claimRequest);
        });

        assertEquals("Customer does not have the required insurance type", exception.getMessage());
        verify(customerRepository, times(1)).findById(customer.getId());
        verify(claimRepository, times(0)).save(any(InsuranceClaim.class));
    }

    @Test
    void testUpdateClaimStatus_Success() {
        when(claimRepository.findById(claim.getId())).thenReturn(Optional.of(claim));
        when(claimRepository.save(any(InsuranceClaim.class))).thenReturn(claim);

        InsuranceClaimResponse response = insuranceClaimService.updateClaimStatus(claim.getId(), ClaimStatus.APPROVED);

        assertNotNull(response);
        assertEquals(ClaimStatus.APPROVED, response.getStatus());
        verify(claimRepository, times(1)).findById(claim.getId());
        verify(claimRepository, times(1)).save(any(InsuranceClaim.class));
    }

    @Test
    void testUpdateClaimStatus_ClaimNotFound() {
        when(claimRepository.findById(claim.getId())).thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class, () -> {
            insuranceClaimService.updateClaimStatus(claim.getId(), ClaimStatus.APPROVED);
        });

        verify(claimRepository, times(1)).findById(claim.getId());
        verify(claimRepository, times(0)).save(any(InsuranceClaim.class));
    }

    @Test
    void testDeleteClaim_Success() {
        when(claimRepository.findById(claim.getId())).thenReturn(Optional.of(claim));

        insuranceClaimService.deleteClaim(claim.getId());

        verify(claimRepository, times(1)).findById(claim.getId());
        verify(claimRepository, times(1)).delete(claim);
    }

    @Test
    void testDeleteClaim_ClaimNotFound() {
        when(claimRepository.findById(claim.getId())).thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class, () -> {
            insuranceClaimService.deleteClaim(claim.getId());
        });

        verify(claimRepository, times(1)).findById(claim.getId());
        verify(claimRepository, times(0)).delete(any(InsuranceClaim.class));
    }
}
