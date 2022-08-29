package com.safetynet.safetynetsystem.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class FireStation extends AbstractBaseEntity {

    @NotBlank
    private String station;
    @NotBlank
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FireStation that = (FireStation) o;
        return station != null && Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
