package com.bhaumik18.medisync.controller;

import com.bhaumik18.medisync.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldRegisterPatientAndReturnToken() throws Exception {
        // We use a timestamped email to avoid MongoDB unique index collisions when running tests multiple times
        String uniqueEmail = "test" + System.currentTimeMillis() + "@medisync.com";
        
        RegisterRequest request = RegisterRequest.builder()
                .firstName("Integration")
                .lastName("Test")
                .email(uniqueEmail)
                .password("SecurePass123!")
                .build();

        // Simulating the exact Postman request you just made
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // Expect 200 OK
                .andExpect(jsonPath("$.token").exists()); // Expect a JWT in the response
    }
    
    @Test
    void shouldBlockUnauthorizedAccessToMeEndpoint() throws Exception {
        // Hitting the protected endpoint without a token
        mockMvc.perform(get("/api/v1/patients/me"))
                .andExpect(status().isForbidden()); // Expect 403 Forbidden
    }
}