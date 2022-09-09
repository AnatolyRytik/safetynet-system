package com.safetynet.safetynetsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Getter
public class FloodResponseDTO {
    private String address;
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private Set<String> station;
    private Set<String> medications;
    private Set<String> allergies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FloodResponseDTO)) return false;
        FloodResponseDTO that = (FloodResponseDTO) o;
        return age == that.age && Objects.equals(address, that.address) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phone, that.phone) && Objects.equals(station, that.station) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, firstName, lastName, phone, age, station, medications, allergies);
    }
}
