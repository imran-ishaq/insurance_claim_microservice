package com.example.demo.repository;

import com.example.demo.entity.InsuranceClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, UUID> {
    List<InsuranceClaim> findByUserId(UUID userId);
}
