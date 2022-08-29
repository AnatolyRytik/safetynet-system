package com.safetynet.safetynetsystem.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class StationCoverageDTO {

    private ArrayList<PersonShortDataDTO> personList;
    private int adultQuantity;
    private int kidsQuantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationCoverageDTO)) return false;
        StationCoverageDTO that = (StationCoverageDTO) o;
        return adultQuantity == that.adultQuantity && kidsQuantity == that.kidsQuantity && Objects.equals(personList, that.personList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personList, adultQuantity, kidsQuantity);
    }
}
