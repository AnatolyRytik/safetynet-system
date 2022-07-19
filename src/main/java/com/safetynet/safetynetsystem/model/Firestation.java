package com.safetynet.safetynetsystem.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Firestation extends AbstractBaseEntity {

    private String station;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Firestation that = (Firestation) o;
        return station != null && Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
