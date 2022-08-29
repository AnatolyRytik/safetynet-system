package com.safetynet.safetynetsystem.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class PersonUtil {

    public static int calculateAge(Date birthDate) {
        LocalDate birthDateLocal = convertToLocalDateViaInstant(birthDate);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDateLocal, currentDate).getYears();
    }

    private static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
