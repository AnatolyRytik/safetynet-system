package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.service.FireStationService;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class FireStationController {
    private static final Logger log = LogManager.getLogger(FireStationController.class);
    private final FireStationService firestationService;

    public FireStationController(FireStationService firestationService) {
        this.firestationService = firestationService;
    }

    @PostMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity<FireStation> create(@Valid @RequestBody FireStation firestation) {
        log.info("create fire station ={}", firestation);
        FireStation created = firestationService.save(firestation);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity update(@Valid @RequestBody FireStation firestation) {
        log.info("update fire station ={}", firestation);
        FireStation fireStationUpdated;
        try {
            fireStationUpdated = firestationService.update(firestation);
        } catch (NotFoundException e) {
            log.error("not found fire station ={}", firestation);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("FireStation not found");
        }
        log.info("successful update fire station ={}", firestation);
        return new ResponseEntity<>(fireStationUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/firestation")
    public ResponseEntity delete(@Valid @RequestBody FireStation firestation) {
        log.info("delete fire station ={}", firestation);
        try {
            firestationService.delete(firestation);
        } catch (NotFoundException e) {
            log.error("not found fire station ={}", firestation);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("FireStation not found");
        }
        log.info("successful delete fire station ={}", firestation);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
