package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.dto.*;
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

    public StationCoverageDTO getPersonByStationNumber(String station) {
        List<FireStation> fireStationList = firestationRepository.findByStation(station).orElseThrow(() -> new NotFoundException(
                ("Fire station not found by station number")));

        List<Person> personList = new ArrayList<>();

        for (FireStation fireStation : fireStationList) {
            List<Person> newPersonList;
            newPersonList = personRepository.findByAddress(fireStation.getAddress()).orElseThrow(() -> new NotFoundException(
                    ("Person by address not found")));
            personList.addAll(newPersonList);
        }

        List<PersonShortDataDTO> personShortData = personList.stream()
                .map(PersonShortDataDTO::new)
                .collect(Collectors.toList());

        int adultCount = 0;
        int childCount = 0;

        for (Person person : personList) {
            int age = calculateAge(person.getFirstName(), person.getLastName());
            if (age >= 18) {
                adultCount++;
            } else childCount++;
        }

        return new StationCoverageDTO(personShortData, adultCount, childCount);
    }

    public List<ChildAlertDTO> getChildAlert(String address) {
        List<Person> personList;
        personList = personRepository.findByAddress(address).orElseThrow(() -> new NotFoundException(
                ("Person by address not found")));

        return getChildAlertList(personList);
    }

    private List<ChildAlertDTO> getChildAlertList(List<Person> personList) {
        List<ChildAlertDTO> kids = new ArrayList<>();
        for (Person person : personList) {
            int age = calculateAge(person.getFirstName(), person.getLastName());
            if (age <= 18) {
                List<Person> familyMember = personRepository.findByLastName(person.getLastName()).orElseThrow(() -> new NotFoundException(
                        ("Persons by last name not found")));

                List<PersonShortDataDTO> familyMembers = familyMember.stream()
                        .filter(p -> !p.getFirstName().equals(person.getFirstName()))
                        .map(PersonShortDataDTO::new)
                        .collect(Collectors.toList());

                ChildAlertDTO childAlertDTO = new ChildAlertDTO(person.getFirstName(), person.getLastName(), age, familyMembers);
                kids.add(childAlertDTO);
            }
        }
        return kids;
    }

    public List<String> getPhoneNumbersByFireStation(String station) {
        List<FireStation> fireStationList = firestationRepository.findByStation(station).orElseThrow(() -> new NotFoundException(
                ("Fire station not found by station number")));

        List<Person> personList = new ArrayList<>();
        for (FireStation fireStation : fireStationList) {
            personList.addAll(personRepository.findByAddress(fireStation.getAddress()).orElseThrow(() -> new NotFoundException(
                    ("Person by address not found"))));
        }

        return personList.stream()
                .map(Person::getPhone)
                .collect(Collectors.toList());
    }

    public List<FireAlertDTO> getPersonByAddress(String address) {
        List<Person> personList = personRepository.findByAddress(address).orElseThrow(() -> new NotFoundException(
                ("Person by address not found")));
        FireStation fireStation = firestationRepository.findByAddress(address).orElseThrow(() -> new NotFoundException(
                ("FireStation by address not found")));
        List<FireAlertDTO> fireAlertDTOList = new ArrayList<>();
        for (Person person : personList) {
            MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName()).orElseThrow(() -> new NotFoundException(
                    ("Medical records by firstName and lastName not found")));

            int age = PersonUtil.calculateAge(medicalRecord.getBirthdate());

            FireAlertDTO fireAlertDTO = new FireAlertDTO(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getPhone(),
                    age,
                    fireStation.getStation(),
                    medicalRecord.getMedications(),
                    medicalRecord.getAllergies());

            fireAlertDTOList.add(fireAlertDTO);
        }
        return fireAlertDTOList;
    }

    public List<FloodAlertDTO> getHouseholdByFireStation(List<String> stationNumber) {
        List<String> addressList = stationNumber.stream()
                .map(station -> firestationRepository.findByStation(station).orElseThrow(() -> new NotFoundException(
                        ("Fire station not found"))))
                .flatMap(List::stream)
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        List<FloodAlertDTO> floodAlertDTOList = new ArrayList<>();
        for (String address : addressList) {
            List<FireAlertDTO> personList = getPersonByAddress(address);
            FloodAlertDTO floodAlertDTO = new FloodAlertDTO(address, personList);
            floodAlertDTOList.add(floodAlertDTO);
        }
        return floodAlertDTOList;
    }

    private int calculateAge(String firstName, String lastName) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new NotFoundException(
                ("Medical record by first name and last name not found")));
        return PersonUtil.calculateAge(medicalRecord.getBirthdate());
    }
}
