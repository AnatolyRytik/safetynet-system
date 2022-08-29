package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.dto.*;
import com.safetynet.safetynetsystem.service.SafetyNetService;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
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
        List<FireResponseDTO> fireAlertDTOList = safetyNetService.getPersonByAddress(address);
        return new ResponseEntity<>(fireAlertDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/flood/stations", produces = "application/json")
    public ResponseEntity getHouseholdByFireStation(@RequestParam List<String> stations) {
        List<FloodResponseDTO> floodAlertDTOList = safetyNetService.getHouseholdByFireStation(stations);
        return new ResponseEntity<>(floodAlertDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/personInfo", produces = "application/json")
    public ResponseEntity getPersonInfo(@RequestParam String firstName,
                                        @RequestParam String lastName) {
        PersonInfoDTO personUpdated;
        try {
            personUpdated = safetyNetService.getPersonInfo(firstName, lastName);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Person not found");
        }
        return new ResponseEntity<>(personUpdated, HttpStatus.OK);
    }

    @GetMapping(value = "/communityEmail", produces = "application/json")
    public ResponseEntity getCommunityEmail(@RequestParam String city) {
        List<String> emailList = safetyNetService.getCommunityEmail(city);
        return new ResponseEntity<>(emailList, HttpStatus.OK);
    }
}
