package com.example.demo.mapper;

import com.example.demo.dto.request.InsuranceClaimRequest;
import com.example.demo.dto.response.InsuranceClaimResponse;
import com.example.demo.entity.InsuranceClaim;
import com.example.demo.enums.ClaimStatus;


public class InsuranceClaimMapper {

    public static InsuranceClaim toEntity(InsuranceClaimRequest request) {
        return InsuranceClaim.builder()
                .userId(request.getUserId())
                .claimType(request.getClaimType())
                .date(request.getDate())
                .status(ClaimStatus.OPEN)
                .cost(request.getCost())
                .build();
    }

    public static InsuranceClaimResponse toResponse(InsuranceClaim claim) {
        return InsuranceClaimResponse.builder()
                .id(claim.getId())
                .userId(claim.getUserId())
                .claimType(claim.getClaimType())
                .date(claim.getDate())
                .status(claim.getStatus())
                .cost(claim.getCost())
                .build();
    }
}

