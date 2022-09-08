package com.safetynet.safetynetsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class MedicalRecord extends AbstractBaseEntity {

    @JsonFormat(pattern = "MM/dd/yyyy")
    String birthdate;
    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> medications;
    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> allergies;
    @NotBlank
    @NaturalId
    private String firstName;
    @NotBlank
    @NaturalId
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalRecord that)) return false;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthdate, that.birthdate) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthdate, medications, allergies, firstName, lastName);
    }


}