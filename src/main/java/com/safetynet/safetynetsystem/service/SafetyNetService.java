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
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public StationCoverageDTO getPersonByStationNumber(String station) throws RuntimeException {
        Assert.notNull(station, "Fire station number must not be null");
        List<FireStation> fireStationList = firestationRepository.findByStation(station);
        if (fireStationList.isEmpty()) {
            throw new NotFoundException(("Fire stations not found by station number"));
        }

        List<Person> persons = new ArrayList<>();

        for (FireStation fireStation : fireStationList) {
            List<Person> newPersonList = personRepository.findByAddress(fireStation.getAddress());
            persons.addAll(newPersonList);
        }

        ArrayList<PersonShortDataDTO> personShortData = (ArrayList<PersonShortDataDTO>) persons.stream()
                .map(PersonShortDataDTO::new)
                .collect(Collectors.toList());

        int adultCount = 0;
        int childCount = 0;

        for (Person person : persons) {
            int age = calculateAge(person.getFirstName(), person.getLastName());
            if (age >= 18) {
                adultCount++;
            } else childCount++;
        }

        return new StationCoverageDTO(personShortData, adultCount, childCount);
    }

    public List<ChildAlertDTO> getChildAlert(String address) throws RuntimeException {
        Assert.notNull(address, "address station must not be null");
        List<Person> persons = personRepository.findByAddress(address);
        if (persons.isEmpty()) {
            throw new NotFoundException("Person by address not found");
        }
        return getChildAlertList(persons);
    }

    private List<ChildAlertDTO> getChildAlertList(List<Person> persons) throws RuntimeException {
        List<ChildAlertDTO> kids = new ArrayList<>();
        for (Person person : persons) {
            int age = calculateAge(person.getFirstName(), person.getLastName());
            if (age <= 18) {
                List<Person> familyMember = personRepository.findByLastName(person.getLastName());

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

    public List<String> getPhoneNumbersByFireStation(String station) throws RuntimeException {
        Assert.notNull(station, "Station number must not be null");
        List<FireStation> fireStationList = firestationRepository.findByStation(station);
        if (fireStationList.isEmpty()) {
            throw new NotFoundException(("Fire stations not found by station number"));
        }
        List<Person> persons = new ArrayList<>();
        for (FireStation fireStation : fireStationList) {
            persons.addAll(personRepository.findByAddress(fireStation.getAddress()));
        }

        return persons.stream()
                .map(Person::getPhone)
                .collect(Collectors.toList());
    }

    public List<FireResponseDTO> getPersonByAddress(String address) throws RuntimeException {
        Assert.notNull(address, "address station must not be null");
        List<Person> persons = personRepository.findByAddress(address);
        if (persons.isEmpty()) {
            throw new NotFoundException("Person by address not found");
        }
        List<FireStation> fireStation = firestationRepository.findByAddress(address);
        List<FireResponseDTO> fireAlerts = new ArrayList<>();
        for (Person person : persons) {
            MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                    .orElseThrow(() -> new NotFoundException(("Medical records by firstName and lastName not found")));

            int age = PersonUtil.calculateAge(medicalRecord.getBirthdate());

            FireResponseDTO fireAlertDTO = new FireResponseDTO(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getPhone(),
                    age,
                    fireStation.stream()
                            .map(FireStation::getStation)
                            .collect(Collectors.toSet()),
                    medicalRecord.getMedications(),
                    medicalRecord.getAllergies());

            fireAlerts.add(fireAlertDTO);
        }
        return fireAlerts;
    }

    public List<FloodResponseDTO> getHouseholdByFireStation(List<String> stationNumber) throws RuntimeException {
        Assert.notNull(stationNumber, "Station number must not be null");
        Set<String> fireStationAddresses = getFireStationsAddresses(stationNumber);
        List<FloodResponseDTO> household = new ArrayList<>();

        for (String address : fireStationAddresses) {
            List<Person> persons = personRepository.findByAddress(address);
            if (persons.isEmpty()) {
                throw new NotFoundException("Person by address not found");
            }
            List<FireStation> fireStation = firestationRepository.findByAddress(address);

            for (Person person : persons) {
                MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                        .orElseThrow(() -> new NotFoundException(("Medical records by firstName and lastName not found")));

                int age = PersonUtil.calculateAge(medicalRecord.getBirthdate());

                FloodResponseDTO floodResponseDTO = new FloodResponseDTO(
                        address,
                        person.getFirstName(),
                        person.getLastName(),
                        person.getPhone(),
                        age,
                        fireStation.stream()
                                .map(FireStation::getStation)
                                .collect(Collectors.toSet()),
                        medicalRecord.getMedications(),
                        medicalRecord.getAllergies());

                household.add(floodResponseDTO);
            }
        }
        return household;
    }

    private Set<String> getFireStationsAddresses(List<String> stationNumbers) throws RuntimeException {
        List<FireStation> fireStations = new ArrayList<>();
        for (String stationNumber : stationNumbers) {
            List<FireStation> fireStations1 = firestationRepository.findByStation(stationNumber);
            if (fireStations1.isEmpty()) {
                throw new NotFoundException("Fire stations not found by station number");
            }
            fireStations.addAll(fireStations1);
        }

        return fireStations.stream()
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());
    }

    public PersonInfoDTO getPersonInfo(String firstName, String lastName) throws RuntimeException {
        Person person = personRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException("Person not found"));

        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException("Medical record not found"));

        int age = calculateAge(firstName, lastName);

        return new PersonInfoDTO(person.getFirstName(),
                person.getLastName(), person.getAddress(),
                age, person.getEmail(), medicalRecord.getAllergies(), medicalRecord.getMedications());
    }

    public List<String> getCommunityEmail(String city) throws RuntimeException {
        Assert.notNull(city, "city must not be null");
        List<Person> persons = personRepository.findByCity(city);
        if (persons.isEmpty()) {
            throw new NotFoundException("Person by address not found");
        }

        return persons.stream()
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }

    private int calculateAge(String firstName, String lastName) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException(("Medical record by first name and last name not found")));
        return PersonUtil.calculateAge(medicalRecord.getBirthdate());
    }
}
