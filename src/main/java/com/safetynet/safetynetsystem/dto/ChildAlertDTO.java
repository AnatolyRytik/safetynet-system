package com.safetynet.safetynetsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
@ToString
public class ChildAlertDTO {

    private String firstName;
    private String lastName;
    private int age;
    private List<PersonShortDataDTO> family;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildAlertDTO)) return false;
        ChildAlertDTO that = (ChildAlertDTO) o;
        return age == that.age && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(family, that.family);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, family);
    }
}
