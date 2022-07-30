package com.safetynet.safetynetsystem.model;

import com.safetynet.safetynetsystem.dto.PersonShortData;
import lombok.*;

import javax.persistence.Entity;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Person extends AbstractBaseEntity {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(address, person.address) && Objects.equals(city, person.city) && Objects.equals(zip, person.zip) && Objects.equals(phone, person.phone) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, city, zip, phone, email);
    }

    public PersonShortData getShortData() {
        return new PersonShortData(firstName, lastName, address, phone);
    }
}
