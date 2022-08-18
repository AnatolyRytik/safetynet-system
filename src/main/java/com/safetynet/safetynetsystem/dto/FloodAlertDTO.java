package com.safetynet.safetynetsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FloodAlertDTO {
    private String address;
    private List<FireAlertDTO> fireAlertPersonList;
}
