package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.service.MedicalRecordService;
import com.safetynet.safetynetsystem.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping(value = "/medicalrecord", produces = "application/json")
    public ResponseEntity<List> getAll() {
        return ResponseEntity.ok()
                .body(medicalRecordService.getAll());
    }

    @GetMapping(value = "/medicalrecord/{id}", produces = "application/json")
    public ResponseEntity<MedicalRecord> getById(@PathVariable("id") Integer id) {
        MedicalRecord medicalRecord = medicalRecordService.getById(id);
        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @PostMapping(value = "/medicalrecord", produces = "application/json")
    public ResponseEntity<MedicalRecord> create(@Valid @RequestBody MedicalRecord medicalRecord) {
        ValidationUtil.checkNew(medicalRecord);
        MedicalRecord created = medicalRecordService.save(medicalRecord);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/medicalrecord/{id}", produces = "application/json")
    public ResponseEntity<MedicalRecord> updateById(@Valid @RequestBody MedicalRecord medicalRecord,
                                                    @PathVariable("id") Integer id) {
        ValidationUtil.assureIdConsistent(medicalRecord, id);
        return new ResponseEntity<>(medicalRecordService.update(medicalRecord, id), HttpStatus.OK);
    }

    @DeleteMapping("/medicalrecord/{id}")
    public ResponseEntity<MedicalRecord> deleteById(@PathVariable("id") Integer id) {
        medicalRecordService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
