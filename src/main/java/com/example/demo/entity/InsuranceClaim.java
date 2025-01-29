package com.example.demo.entity;

import com.example.demo.enums.ClaimStatus;
import com.example.demo.enums.ClaimType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceClaim extends AbstractEntity {

    @Column(name = "claim_type", nullable = false)
    private ClaimType claimType;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ClaimStatus status;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "user_id", nullable = false)
    private UUID userId;
}
