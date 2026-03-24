package com.bhaumik18.medisync.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bhaumik18.medisync.model.PatientProfile;

@Repository
public interface PatientRepository extends MongoRepository<PatientProfile, String> {
	Optional<PatientProfile> findByEmail(String email);
}
