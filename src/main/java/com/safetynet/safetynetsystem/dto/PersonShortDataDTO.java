package com.safetynet.safetynetsystem.dto;

import com.safetynet.safetynetsystem.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class PersonShortDataDTO {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phone;

    public PersonShortDataDTO(Person person) {
        firstName = person.getFirstName();
        lastName = person.getLastName();
        address = person.getAddress();
        phone = person.getPhone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonShortDataDTO)) return false;
        PersonShortDataDTO that = (PersonShortDataDTO) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, phone);
    }
}
