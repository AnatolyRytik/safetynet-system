package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.repository.MedicalRecordRepository;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        Assert.notNull(medicalRecord, "Medical record must not be null");
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord updateByFirstNameAndLastName(MedicalRecord medicalRecord, String firstName, String lastName) {
        Assert.notNull(medicalRecord, "medicalRecord must not be null");
        MedicalRecord medicalRecordUpdated = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new NotFoundException(
                ("Medical record not found")));
        medicalRecordRepository.delete(medicalRecordUpdated);
        return medicalRecordRepository.save(medicalRecord);
    }

    public void deleteByFirstNameAndLastName(String firstName, String lastName) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new NotFoundException(
                ("Medical record not found")));
        medicalRecordRepository.delete(medicalRecord);
    }
}
