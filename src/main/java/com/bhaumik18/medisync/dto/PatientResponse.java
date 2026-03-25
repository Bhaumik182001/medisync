package com.bhaumik18.medisync.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientResponse {
	private String id;
	private String firstName;
	private String lastName;
	private String email;
}
