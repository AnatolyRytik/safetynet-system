package com.safetynet.safetynetsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ChildAlertDTO {

    private String firstName;
    private String lastName;
    private int age;
    private List<PersonShortDataDTO> family;
}
