package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.service.MedicalRecordService;
import com.safetynet.safetynetsystem.util.ValidationUtil;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping(value = "/medicalRecord", produces = "application/json")
    public ResponseEntity<MedicalRecord> create(@Valid @RequestBody MedicalRecord medicalRecord) {
        ValidationUtil.checkNew(medicalRecord);
        MedicalRecord created = medicalRecordService.save(medicalRecord);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> deleteByFirstNameAndLastName(@RequestParam String firstName,
                                                                      @RequestParam String lastName) {
        try {
            medicalRecordService.deleteByFirstNameAndLastName(firstName, lastName);
        } catch (NotFoundException e) {
            //log
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/medicalRecord", produces = "application/json")
    public ResponseEntity<MedicalRecord> updateByFirstNameAndLastName(@Valid @RequestBody MedicalRecord medicalRecord,
                                                                      @RequestParam String firstName,
                                                                      @RequestParam String lastName) {
        MedicalRecord medicalRecordUpdated;
        try {
            medicalRecordUpdated = medicalRecordService.updateByFirstNameAndLastName(medicalRecord, firstName, lastName);
        } catch (NotFoundException e) {
            //log
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<MedicalRecord>(medicalRecordUpdated, HttpStatus.OK);
    }
}
