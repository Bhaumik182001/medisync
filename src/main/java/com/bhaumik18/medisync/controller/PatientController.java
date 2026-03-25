package com.bhaumik18.medisync.controller;

import com.bhaumik18.medisync.dto.PatientResponse;
import com.bhaumik18.medisync.model.PatientProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @GetMapping("/me")
    public ResponseEntity<PatientResponse> getCurrentPatient(
            @AuthenticationPrincipal PatientProfile currentPatient
    ) {
        // Map the database entity to our safe DTO
        PatientResponse response = PatientResponse.builder()
                .id(currentPatient.getId())
                .firstName(currentPatient.getFirstName())
                .lastName(currentPatient.getLastName())
                .email(currentPatient.getEmail())
                .build();

        return ResponseEntity.ok(response);
    }
}