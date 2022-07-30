package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.dto.PersonShortData;
import com.safetynet.safetynetsystem.dto.StationCoverageDTO;
import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.repository.FireStationRepository;
import com.safetynet.safetynetsystem.repository.MedicalRecordRepository;
import com.safetynet.safetynetsystem.repository.PersonRepository;
import com.safetynet.safetynetsystem.util.PersonUtil;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SafetyNetService {
    private final FireStationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public SafetyNetService(FireStationRepository firestationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public StationCoverageDTO getPersonsByStationNumber(String station) {
        List<FireStation> fireStationList = firestationRepository.findByStation(station).orElseThrow(() -> new NotFoundException(
                ("Fire station not found by station number")));

        List<Person> personList = new ArrayList<>();

        for (FireStation fireStation : fireStationList) {
            List<Person> newPersonList = new ArrayList<>();
            newPersonList = personRepository.findByAddress(fireStation.getAddress()).orElseThrow(() -> new NotFoundException(
                    ("Person by address not found")));
            personList.addAll(newPersonList);
        }

        StationCoverageDTO stationCoverageDTO = new StationCoverageDTO();
        List<PersonShortData> personShortData = personList.stream()
                .map(e -> e.getShortData())
                .collect(Collectors.toList());

        stationCoverageDTO.setPersonList(personShortData);

        int adultCount = 0;
        int childCount = 0;

        for (Person person : personList) {
            MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName()).orElseThrow(() -> new NotFoundException(
                    ("Medical record by first name and last name not found")));

            int age = PersonUtil.calculateAge(medicalRecord.getBirthdate());
            if (age >= 18) {
                adultCount++;
            } else childCount++;
        }

        stationCoverageDTO.setAdultQuantity(adultCount);
        stationCoverageDTO.setKidsQuantity(childCount);

        return stationCoverageDTO;
    }
}
