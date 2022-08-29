package com.safetynet.safetynetsystem.data;

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
        return new MedicalRecord(new Date(1975, Calendar.JUNE, 12), Collections.emptyList(), Collections.emptyList(), "", "");
    }

    public static MedicalRecord getMedicalRecordToAdd() {
        return new MedicalRecord((new Date(1975, Calendar.JUNE, 12)), Arrays.asList("ibupurin:200mg", "hydrapermazol:400mg"), Arrays.asList("nillacilan"), "Nickolas", "Cage");
    }

    public static MedicalRecord getMedicalRecordToUpdate() {
        return new MedicalRecord((new Date(1984, Calendar.JUNE, 3)), Arrays.asList("doliprane:1000mg"), Collections.emptyList(), "John", "Boyd");
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
}