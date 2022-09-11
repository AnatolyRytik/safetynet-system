package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.dto.*;
import com.safetynet.safetynetsystem.service.SafetyNetService;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class SafetyNetController {
    private final SafetyNetService safetyNetService;

    public SafetyNetController(SafetyNetService safetyNetService) {
        this.safetyNetService = safetyNetService;
    }

    @GetMapping(value = "/firestation", produces = "application/json")
    public ResponseEntity getPersonsByStationNumber(@RequestParam String stationNumber) {
        log.info("get persons by station number= {}", stationNumber);
        StationCoverageDTO stationCoverageDTO;
        try {
            stationCoverageDTO = safetyNetService.getPersonByStationNumber(stationNumber);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Station not found");
        }
        return new ResponseEntity<>(stationCoverageDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/childAlert", produces = "application/json")
    public ResponseEntity getChildAlert(@RequestParam String address) {
        log.info("get list of children by station address= {}", address);
        List<ChildAlertDTO> kids;
        try {
            kids = safetyNetService.getChildAlert(address);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Address not found");
        }
        return new ResponseEntity<>(kids, HttpStatus.OK);
    }

    @GetMapping(value = "/phoneAlert", produces = "application/json")
    public ResponseEntity getPhoneAlert(@RequestParam String station) {
        log.info("get list of phone number by station number= {}", station);
        List<String> phoneNumbers;
        try {
            phoneNumbers = safetyNetService.getPhoneNumbersByFireStation(station);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Station not found");
        }
        return new ResponseEntity<>(phoneNumbers, HttpStatus.OK);
    }

    @GetMapping(value = "/fire", produces = "application/json")
    public ResponseEntity getPersonByAddress(@RequestParam String address) {
        log.info("in case of fire get persons by address= {}", address);
        List<FireResponseDTO> fireAlertDTOList;
        try {
            fireAlertDTOList = safetyNetService.getPersonByAddress(address);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Address not found");
        }
        return new ResponseEntity<>(fireAlertDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/flood/stations", produces = "application/json")
    public ResponseEntity getHouseholdByFireStation(@RequestParam List<String> stations) {
        log.info("in case of flood get persons by stations=");
        List<FloodResponseDTO> floodAlertDTOList;
        try {
            floodAlertDTOList = safetyNetService.getHouseholdByFireStation(stations);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Station not found");
        }
        return new ResponseEntity<>(floodAlertDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/personInfo", produces = "application/json")
    public ResponseEntity getPersonInfo(@RequestParam String firstName,
                                        @RequestParam String lastName) {
        log.info("get person info by name= {} and lastname = {}", firstName, lastName);
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
        log.info("get list of emails from city= {}", city);
        List<String> emailList;
        try {
            emailList = safetyNetService.getCommunityEmail(city);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("City not found");
        }
        return new ResponseEntity<>(emailList, HttpStatus.OK);
    }
}
