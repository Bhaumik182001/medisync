package com.bhaumik18.medisync.service;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.bhaumik18.medisync.dto.AuthenticationResponse;
import com.bhaumik18.medisync.dto.LoginRequest;
import com.bhaumik18.medisync.dto.RegisterRequest;
import com.bhaumik18.medisync.model.PatientProfile;
import com.bhaumik18.medisync.repository.PatientRepository;
import com.bhaumik18.medisync.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final PatientRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		var patient  = PatientProfile.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.roles(Set.of("PATIENT"))
				.build();
		
		repository.save(patient);
		
		var jwtToken = jwtService.generateToken(patient);
		
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
	
	public AuthenticationResponse authenticate(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
		);
		
		var patient = repository.findByEmail(request.getEmail()).orElseThrow();
		
		var jwtToken = jwtService.generateToken(patient);
		
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
}
