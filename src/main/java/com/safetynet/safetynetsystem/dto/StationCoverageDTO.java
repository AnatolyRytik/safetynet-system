package com.safetynet.safetynetsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StationCoverageDTO {

    private List<PersonShortData> personList;
    private int adultQuantity;
    private int kidsQuantity;
}
