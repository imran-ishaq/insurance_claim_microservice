package com.example.demo.dto.request;

import com.example.demo.enums.ClaimStatus;
import com.example.demo.enums.ClaimType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class InsuranceClaimRequest {
    private UUID userId;
    private ClaimType claimType;
    private LocalDate date;
    private Double cost;
}
