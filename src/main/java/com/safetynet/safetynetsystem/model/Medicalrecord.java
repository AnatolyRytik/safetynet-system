package com.safetynet.safetynetsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Medicalrecord extends AbstractBaseEntity {

    String firstName;
    String lastName;

    @JsonFormat(pattern = "MM/dd/yyyy")
    Date birthdate;

    @ElementCollection
    List<String> medications;

    @ElementCollection
    List<String> allergies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicalrecord)) return false;
        Medicalrecord that = (Medicalrecord) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthdate, that.birthdate) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthdate, medications, allergies);
    }
}