package com.safetynet.safetynetsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class FireResponseDTO {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private String station;
    private List<String> medications;
    private List<String> allergies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FireResponseDTO)) return false;
        FireResponseDTO that = (FireResponseDTO) o;
        return age == that.age && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phone, that.phone) && Objects.equals(station, that.station) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phone, age, station, medications, allergies);
    }
}
