package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.dto.*;
import com.safetynet.safetynetsystem.service.SafetyNetService;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SafetyNetController {
    private static final Logger log = LogManager.getLogger(SafetyNetController.class);
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
            log.error("Not found station number= {}", stationNumber);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Station not found");
        }
        log.info("search result= {}", stationCoverageDTO.toString());
        return new ResponseEntity<>(stationCoverageDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/childAlert", produces = "application/json")
    public ResponseEntity getChildAlert(@RequestParam String address) {
        log.info("get list of children by station address= {}", address);
        List<ChildAlertDTO> kids;
        try {
            kids = safetyNetService.getChildAlert(address);
        } catch (NotFoundException e) {
            log.error("Not found station address= {}", address);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Address not found");
        }
        log.info("search result= {}", kids.toString());
        return new ResponseEntity<>(kids, HttpStatus.OK);
    }

    @GetMapping(value = "/phoneAlert", produces = "application/json")
    public ResponseEntity getPhoneAlert(@RequestParam String station) {
        log.info("get list of phone number by station number= {}", station);
        List<String> phoneNumbers;
        try {
            phoneNumbers = safetyNetService.getPhoneNumbersByFireStation(station);
        } catch (NotFoundException e) {
            log.error("Not found station number= {}", station);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Station not found");
        }
        log.info("search result= {}", phoneNumbers.toString());
        return new ResponseEntity<>(phoneNumbers, HttpStatus.OK);
    }

    @GetMapping(value = "/fire", produces = "application/json")
    public ResponseEntity getPersonByAddress(@RequestParam String address) {
        log.info("in case of fire get persons by address= {}", address);
        List<FireResponseDTO> fireAlertDTOList;
        try {
            fireAlertDTOList = safetyNetService.getPersonByAddress(address);
        } catch (NotFoundException e) {
            log.error("Not found address= {}", address);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Address not found");
        }
        log.info("search result= {}", fireAlertDTOList.toString());
        return new ResponseEntity<>(fireAlertDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/flood/stations", produces = "application/json")
    public ResponseEntity getHouseholdByFireStation(@RequestParam List<String> stations) {
        log.info("in case of flood get persons by stations={}", stations);
        List<FloodResponseDTO> floodAlertDTOList;
        try {
            floodAlertDTOList = safetyNetService.getHouseholdByFireStation(stations);
        } catch (NotFoundException e) {
            log.error("in case of flood get persons by stations={}", stations);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Station not found");
        }
        log.info("search result= {}", floodAlertDTOList.toString());
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
            log.error("Not found person info by name= {} and lastname = {}", firstName, lastName);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Person not found");
        }
        log.info("search result= {}", personUpdated.toString());
        return new ResponseEntity<>(personUpdated, HttpStatus.OK);
    }

    @GetMapping(value = "/communityEmail", produces = "application/json")
    public ResponseEntity getCommunityEmail(@RequestParam String city) {
        log.info("get list of emails from city= {}", city);
        List<String> emailList;
        try {
            emailList = safetyNetService.getCommunityEmail(city);
        } catch (NotFoundException e) {
            log.error("Not found city= {}", city);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("City not found");
        }
        log.info("search result= {}", emailList.toString());
        return new ResponseEntity<>(emailList, HttpStatus.OK);
    }
}
