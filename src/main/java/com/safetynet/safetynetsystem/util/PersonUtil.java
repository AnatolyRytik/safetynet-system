package com.safetynet.safetynetsystem.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PersonUtil {

    public static int calculateAge(String birthdate) {
        LocalDate currentDate = LocalDate.now();
        int age = 0;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.FRANCE);
            LocalDate birthDate = LocalDate.parse(birthdate, formatter);
            age = Period.between(birthDate, currentDate).getYears();
            return age;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return age;
    }
}
