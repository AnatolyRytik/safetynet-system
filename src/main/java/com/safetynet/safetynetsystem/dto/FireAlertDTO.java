package com.safetynet.safetynetsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FireAlertDTO {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private String station;
    private List<String> medications;
    private List<String> allergies;
}
