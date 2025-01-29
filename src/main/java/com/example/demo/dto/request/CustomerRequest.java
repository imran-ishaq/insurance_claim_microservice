package com.example.demo.dto.request;

import com.example.demo.enums.ClaimType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String fullName;
    private LocalDate dateOfBirth;
    private List<ClaimType> insuranceTypes;
}
