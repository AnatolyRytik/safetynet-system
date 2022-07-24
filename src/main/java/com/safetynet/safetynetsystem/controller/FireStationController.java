package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.service.FireStationService;
import com.safetynet.safetynetsystem.util.ValidationUtil;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class FireStationController {

    private final FireStationService firestationService;

    public FireStationController(FireStationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity<List<FireStation>> getAll() {
        return ResponseEntity.ok()
                .body(firestationService.getAll());
    }

    @GetMapping(value = "/firestation/{id}", produces = "application/json")
    public ResponseEntity<FireStation> getById(@PathVariable("id") Integer id) {
        FireStation fireStation;
        try {
            fireStation = firestationService.getById(id);
        } catch (NotFoundException e) {
            //log
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fireStation, HttpStatus.OK);
    }

    @PostMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity<FireStation> create(@Valid @RequestBody FireStation firestation) {
        ValidationUtil.checkNew(firestation);
        FireStation created = firestationService.save(firestation);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/firestation/{id}", produces = "application/json")
    public ResponseEntity<FireStation> updateById(@Valid @RequestBody FireStation firestation,
                                                  @PathVariable("id") Integer id) {
        ValidationUtil.assureIdConsistent(firestation, id);
        return new ResponseEntity<>(firestationService.update(firestation, id), HttpStatus.OK);
    }

    @DeleteMapping("/firestation/{id}")
    public ResponseEntity<FireStation> deleteById(@PathVariable("id") Integer id) {
        firestationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
