package com.safetynet.safetynetsystem.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MedicalRecords {

    private String firstName;
    private String lastName;
    private Date birthdate;
    private List<String> medications;
    private List<String> allergies;
}