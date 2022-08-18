package com.safetynet.safetynetsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StationCoverageDTO {

    private List<PersonShortDataDTO> personList;
    private int adultQuantity;
    private int kidsQuantity;
}
