package com.safetynet.safetynetsystem.repository;

import com.safetynet.safetynetsystem.model.Medicalrecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordsRepository extends JpaRepository<Medicalrecord, Integer> {
}
