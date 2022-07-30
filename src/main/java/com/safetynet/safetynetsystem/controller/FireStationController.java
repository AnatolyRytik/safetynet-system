package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.service.FireStationService;
import com.safetynet.safetynetsystem.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class FireStationController {

    private final FireStationService firestationService;

    public FireStationController(FireStationService firestationService) {
        this.firestationService = firestationService;
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
