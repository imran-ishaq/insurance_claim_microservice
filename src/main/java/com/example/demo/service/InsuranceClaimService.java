package com.example.demo.service;

import com.example.demo.dto.request.InsuranceClaimRequest;
import com.example.demo.dto.response.InsuranceClaimResponse;
import com.example.demo.entity.Customer;
import com.example.demo.entity.InsuranceClaim;
import com.example.demo.enums.ClaimStatus;
import com.example.demo.exception.ClaimNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.mapper.InsuranceClaimMapper;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.InsuranceClaimRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class InsuranceClaimService {
    private final InsuranceClaimRepository claimRepository;
    private final CustomerRepository customerRepository;

    public InsuranceClaimResponse createClaim(InsuranceClaimRequest request) {
        Customer customer = customerRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        if (!customer.getInsuranceTypes().contains(request.getClaimType())) {
            throw new RuntimeException("Customer does not have the required insurance type");
        }

        InsuranceClaim claim = InsuranceClaimMapper.toEntity(request);
        InsuranceClaim savedClaim = claimRepository.save(claim);
        return InsuranceClaimMapper.toResponse(savedClaim);
    }

    public InsuranceClaimResponse updateClaimStatus(UUID claimId, ClaimStatus status) {
        InsuranceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found"));
        claim.setStatus(status);
        InsuranceClaim updatedClaim = claimRepository.save(claim);
        return InsuranceClaimMapper.toResponse(updatedClaim);
    }

    public void deleteClaim(UUID claimId) {
        InsuranceClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found"));
        claimRepository.delete(claim);
    }
}
