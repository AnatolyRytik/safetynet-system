package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.repository.MedicalRecordRepository;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.safetynet.safetynetsystem.util.ValidationUtil.checkNotFoundWithId;

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

    public List<MedicalRecord> getAll() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getById(Integer id) {
        return medicalRecordRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ("Medical record not found")));
    }

    public MedicalRecord update(MedicalRecord medicalRecord, Integer id) {
        Assert.notNull(medicalRecord, "Medical record must not be null");
        return checkNotFoundWithId(medicalRecordRepository.save(medicalRecord), id);
    }

    public void deleteById(Integer id) {
        checkNotFoundWithId(medicalRecordRepository.findById(id), id);
        medicalRecordRepository.deleteById(id);
    }
}
