package com.safetynet.safetynetsystem.repository;

import com.safetynet.safetynetsystem.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

    Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);

    List<MedicalRecord> findByBirthdateBefore(Date birthdate);
}
