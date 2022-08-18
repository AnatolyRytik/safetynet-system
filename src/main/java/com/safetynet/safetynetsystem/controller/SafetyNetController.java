package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.dto.ChildAlertDTO;
import com.safetynet.safetynetsystem.dto.FireAlertDTO;
import com.safetynet.safetynetsystem.dto.FloodAlertDTO;
import com.safetynet.safetynetsystem.dto.StationCoverageDTO;
import com.safetynet.safetynetsystem.service.SafetyNetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SafetyNetController {
    private final SafetyNetService safetyNetService;

    public SafetyNetController(SafetyNetService safetyNetService) {
        this.safetyNetService = safetyNetService;
    }

    @GetMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity<StationCoverageDTO> getPersonsByStationNumber(@RequestParam String stationNumber) {
        StationCoverageDTO stationCoverageDTO = safetyNetService.getPersonByStationNumber(stationNumber);
        return new ResponseEntity<>(stationCoverageDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/childAlert", produces = "application/json")
    public ResponseEntity getChildAlert(@RequestParam String address) {
        List<ChildAlertDTO> kids = safetyNetService.getChildAlert(address);
        return new ResponseEntity<>(kids, HttpStatus.OK);
    }

    @GetMapping(value = "/phoneAlert", produces = "application/json")
    public ResponseEntity getPhoneAlert(@RequestParam String station) {
        List<String> phoneNumbers = safetyNetService.getPhoneNumbersByFireStation(station);
        return new ResponseEntity<>(phoneNumbers, HttpStatus.OK);
    }

    @GetMapping(value = "/fire", produces = "application/json")
    public ResponseEntity getPersonByAddress(@RequestParam String address) {
        List<FireAlertDTO> fireAlertDTOList = safetyNetService.getPersonByAddress(address);
        return new ResponseEntity<>(fireAlertDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/flood/stations", produces = "application/json")
    public ResponseEntity getHouseholdByFireStation(@RequestParam List<String> stations) {
        List<FloodAlertDTO> floodAlertDTOList = safetyNetService.getHouseholdByFireStation(stations);
        return new ResponseEntity<>(floodAlertDTOList, HttpStatus.OK);
    }


}
