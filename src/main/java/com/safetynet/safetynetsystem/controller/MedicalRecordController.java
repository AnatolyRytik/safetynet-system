package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.service.MedicalRecordService;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping(value = "/medicalRecord", produces = "application/json")
    public ResponseEntity<MedicalRecord> create(@Valid @RequestBody MedicalRecord medicalRecord) {
        MedicalRecord created = medicalRecordService.save(medicalRecord);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/medicalRecord", produces = "application/json")
    public ResponseEntity updateByFirstNameAndLastName(@Valid @RequestBody MedicalRecord medicalRecord,
                                                       @RequestParam String firstName,
                                                       @RequestParam String lastName) {
        MedicalRecord medicalRecordUpdated;
        try {
            medicalRecordUpdated = medicalRecordService.updateByFirstNameAndLastName(medicalRecord, firstName, lastName);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Medical record not found");
        }
        return new ResponseEntity<>(medicalRecordUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity deleteByFirstNameAndLastName(@RequestParam String firstName,
                                                       @RequestParam String lastName) {
        try {
            medicalRecordService.deleteByFirstNameAndLastName(firstName, lastName);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Medical record not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
