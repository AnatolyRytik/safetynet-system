package com.safetynet.safetynetsystem.data;

import com.safetynet.safetynetsystem.dto.ChildAlertDTO;
import com.safetynet.safetynetsystem.dto.FireResponseDTO;
import com.safetynet.safetynetsystem.dto.PersonShortDataDTO;
import com.safetynet.safetynetsystem.dto.StationCoverageDTO;
import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.model.Person;

import java.util.*;


public class CommonTestData {


    public static Person getEmptyPerson() {
        return new Person("", "", "", "", "", "", "");
    }

    public static Person getPersonToAdd() {
        return new Person("Ericson", "Cadigans", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com");
    }

    public static Person getPersonToUpdate() {
        return new Person("John", "Boyd", "1509 Culver St", "Paris", "97451", "841-874-6512", "jaboyd@email.com");
    }

    public static FireStation getEmptyFireStation() {
        return new FireStation("", "");
    }

    public static FireStation getFireStationToAdd() {
        return new FireStation("1", "947 E. Rose Dr");
    }

    public static FireStation getFireStationToUpdate() {
        return new FireStation("4", "947 E. Rose Dr");
    }

    public static MedicalRecord getEmptyMedicalRecord() {
        return new MedicalRecord(new Date(1975, Calendar.JUNE, 12), Collections.emptySet(), Collections.emptySet(), "", "");
    }

    public static MedicalRecord getMedicalRecordToAdd() {
        return new MedicalRecord((new Date(1975, Calendar.JUNE, 12)), Set.of("ibupurin:200mg", "hydrapermazol:400mg"), Set.of("nillacilan"), "Nickolas", "Cage");
    }

    public static MedicalRecord getMedicalRecordToUpdate() {
        return new MedicalRecord((new Date(1984, Calendar.JUNE, 3)), Set.of("doliprane:1000mg"), Collections.emptySet(), "John", "Boyd");
    }

    public static StationCoverageDTO getPersonByStationNumber() {
        return new StationCoverageDTO(
                new ArrayList<>(Arrays.asList(
                        new PersonShortDataDTO("Lily", "Cooper", "489 Manchester St", "841-874-9845"),
                        new PersonShortDataDTO("Tony", "Cooper", "112 Steppes Pl", "841-874-6874"),
                        new PersonShortDataDTO("Ron", "Peters", "112 Steppes Pl", "841-874-8888"),
                        new PersonShortDataDTO("Allison", "Boyd", "112 Steppes Pl", "841-874-9888"))),
                4,
                0);
    }

    public static List<String> getPhoneNumbersByFireStation() {
        return new ArrayList<>(Arrays.asList("841-874-9845", "841-874-6874", "841-874-8888", "841-874-9888"));
    }

    public static List<String> getCommunityEmails() {
        return new ArrayList<>(Arrays.asList("jaboyd@email.com", "drk@email.com", "tenz@email.com", "jaboyd@email.com", "jaboyd@email.com", "drk@email.com", "tenz@email.com", "jaboyd@email.com", "jaboyd@email.com", "tcoop@ymail.com", "lily@email.com", "soph@email.com", "ward@email.com", "zarc@email.com", "reg@email.com", "jpeter@email.com", "jpeter@email.com", "aly@imail.com", "bstel@email.com", "ssanw@email.com", "bstel@email.com", "clivfd@ymail.com", "gramps@email.com"));
    }

    public static List<ChildAlertDTO> getChildAlert() {
        List<ChildAlertDTO> childAlert = new ArrayList<>();
        childAlert.add(new ChildAlertDTO("Zach", "Zemicks", 5,
                new ArrayList<>(Arrays.asList(
                        new PersonShortDataDTO("Sophia", "Zemicks", "892 Downing Ct", "841-874-7878"),
                        new PersonShortDataDTO("Warren", "Zemicks", "892 Downing Ct", "841-874-7512")))));
        return childAlert;
    }

    public static List<FireResponseDTO> getPersonByAddress() {
        List<FireResponseDTO> fireResponse = new ArrayList<>();
        fireResponse.add(new FireResponseDTO("Sophia", "Zemicks", "841-874-7878", 34, "2", Set.of("aznol:60mg",
                "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"), Set.of("peanut", "shellfish", "aznol")));
        fireResponse.add(new FireResponseDTO("Warren", "Zemicks", "841-874-7512", 37, "2", Collections.emptySet(), Collections.emptySet()));
        fireResponse.add(new FireResponseDTO("Zach", "Zemicks", "841-874-7512", 5, "2", Collections.emptySet(), Collections.emptySet()));
        return fireResponse;
    }
}