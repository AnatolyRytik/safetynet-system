package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.dto.StationCoverageDTO;
import com.safetynet.safetynetsystem.service.SafetyNetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SafetyNetController {
    private final SafetyNetService safetyNetService;

    public SafetyNetController(SafetyNetService safetyNetService) {
        this.safetyNetService = safetyNetService;
    }

    @GetMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity<StationCoverageDTO> getPersonsByStationNumber(@RequestParam String stationNumber) {
        StationCoverageDTO stationCoverageDTO = safetyNetService.getPersonsByStationNumber(stationNumber);
        return new ResponseEntity<>(stationCoverageDTO, HttpStatus.OK);
    }
}
