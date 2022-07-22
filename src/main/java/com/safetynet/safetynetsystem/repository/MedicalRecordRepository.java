package com.safetynet.safetynetsystem.repository;

import com.safetynet.safetynetsystem.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
}
