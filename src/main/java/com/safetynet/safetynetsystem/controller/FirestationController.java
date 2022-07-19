package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.Firestation;
import com.safetynet.safetynetsystem.service.FirestationService;
import com.safetynet.safetynetsystem.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class FirestationController {

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity<List> getAll() {
        return ResponseEntity.ok()
                .body(firestationService.getAll());
    }

    @GetMapping(value = "/firestation/{id}", produces = "application/json")
    public ResponseEntity<Firestation> getById(@PathVariable("id") Integer id) {
        Firestation firestation = firestationService.getById(id);
        return new ResponseEntity<>(firestation, HttpStatus.OK);
    }

    @PostMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity<Firestation> create(@Valid @RequestBody Firestation firestation) {
        ValidationUtil.checkNew(firestation);
        Firestation created = firestationService.save(firestation);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/firestation/{id}", produces = "application/json")
    public ResponseEntity<Firestation> updateById(@Valid @RequestBody Firestation firestation,
                                                  @PathVariable("id") Integer id) {
        ValidationUtil.assureIdConsistent(firestation, id);
        return new ResponseEntity<>(firestationService.update(firestation, id), HttpStatus.OK);
    }

    @DeleteMapping("/firestation/{id}")
    public ResponseEntity<Firestation> deleteById(@PathVariable("id") Integer id) {
        firestationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
