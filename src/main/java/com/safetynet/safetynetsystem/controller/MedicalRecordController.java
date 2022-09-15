package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.service.MedicalRecordService;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MedicalRecordController {
    private static final Logger log = LogManager.getLogger(MedicalRecordController.class);
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping(value = "/medicalRecord", produces = "application/json")
    public ResponseEntity<MedicalRecord> create(@Valid @RequestBody MedicalRecord medicalRecord) {
        log.info("save medical record for={} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        MedicalRecord created = medicalRecordService.save(medicalRecord);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/medicalRecord", produces = "application/json")
    public ResponseEntity updateByFirstNameAndLastName(@Valid @RequestBody MedicalRecord medicalRecord,
                                                       @RequestParam String firstName,
                                                       @RequestParam String lastName) {
        log.info("update medical record ={}", medicalRecord);
        MedicalRecord medicalRecordUpdated;
        try {
            medicalRecordUpdated = medicalRecordService.updateByFirstNameAndLastName(medicalRecord, firstName, lastName);
        } catch (NotFoundException e) {
            log.error("Not found medical record for={} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Medical record not found");
        }
        log.info("successful update medical record ={}", medicalRecord);
        return new ResponseEntity<>(medicalRecordUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity deleteByFirstNameAndLastName(@RequestParam String firstName,
                                                       @RequestParam String lastName) {
        log.info("delete medical record for={} {}", firstName, lastName);
        try {
            medicalRecordService.deleteByFirstNameAndLastName(firstName, lastName);
        } catch (NotFoundException e) {
            log.error("not found medical record for={} {}", firstName, lastName);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Medical record not found");
        }
        log.info("successful delete medical record for={} {}", firstName, lastName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
