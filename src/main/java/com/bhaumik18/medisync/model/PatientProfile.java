package com.bhaumik18.medisync.model;

import java.time.Instant;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "patient_profiles")
public class PatientProfile {
	
	@Id
	private String id;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email fomrat")
	@Indexed(unique = true)
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	@NotBlank(message = "First name is required")
	private String firstName;
	
	@NotBlank(message = "Last name is required")
	private String lastName;
	
	private Set<String> roles;
	
	@Builder.Default
	private boolean isActive = true;
	
	@CreatedDate
	private Instant createdAt;
	
	@LastModifiedDate
	private Instant updatedAt;
	
}
