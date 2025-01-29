package com.example.demo.dto.response;

import com.example.demo.enums.ClaimStatus;
import com.example.demo.enums.ClaimType;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceClaimResponse {
    private UUID id;
    private UUID userId;
    private ClaimType claimType;
    private LocalDate date;
    private ClaimStatus status;
    private Double cost;
}
