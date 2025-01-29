package com.example.demo.dto.response;

import com.example.demo.entity.InsuranceClaim;
import com.example.demo.enums.ClaimType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private UUID id;
    private String fullName;
    private LocalDate dateOfBirth;
    private List<ClaimType> insuranceTypes;
    private Set<InsuranceClaim> insuranceClaim;
}
