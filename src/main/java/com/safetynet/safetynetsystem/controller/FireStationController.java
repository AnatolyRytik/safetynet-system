package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.service.FireStationService;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
public class FireStationController {

    private final FireStationService firestationService;

    public FireStationController(FireStationService firestationService) {
        this.firestationService = firestationService;
    }

    @PostMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity<FireStation> create(@Valid @RequestBody FireStation firestation) {
        log.info("create fire station ={}", firestation.getStation());
        FireStation created = firestationService.save(firestation);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity update(@Valid @RequestBody FireStation firestation) {
        log.info("update fire station ={}", firestation.getStation());
        FireStation fireStationUpdated;
        try {
            fireStationUpdated = firestationService.update(firestation);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("FireStation not found");
        }
        return new ResponseEntity<>(fireStationUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/firestation")
    public ResponseEntity delete(@Valid @RequestBody FireStation firestation) {
        log.info("delete fire station ={}", firestation.getStation());
        try {
            firestationService.delete(firestation);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("FireStation not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
