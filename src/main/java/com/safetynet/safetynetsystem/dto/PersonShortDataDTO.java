package com.safetynet.safetynetsystem.dto;

import com.safetynet.safetynetsystem.model.Person;
import lombok.Getter;

@Getter
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

}
