package com.example.demo.controller;

import com.example.demo.dto.request.InsuranceClaimRequest;
import com.example.demo.dto.response.InsuranceClaimResponse;
import com.example.demo.enums.ClaimStatus;
import com.example.demo.service.InsuranceClaimService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/claims")
@AllArgsConstructor
public class InsuranceClaimController {
    private final InsuranceClaimService claimService;

    @PostMapping
    public ResponseEntity<InsuranceClaimResponse> createClaim(@RequestBody InsuranceClaimRequest request) {
        InsuranceClaimResponse response = claimService.createClaim(request);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{claimId}")
    public ResponseEntity<InsuranceClaimResponse> updateClaimStatus(@PathVariable UUID claimId,
                                                                    @RequestParam ClaimStatus status) {
        InsuranceClaimResponse response = claimService.updateClaimStatus(claimId, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{claimId}")
    public ResponseEntity<Void> deleteClaim(@PathVariable UUID claimId) {
        claimService.deleteClaim(claimId);
        return ResponseEntity.noContent().build();
    }
}
