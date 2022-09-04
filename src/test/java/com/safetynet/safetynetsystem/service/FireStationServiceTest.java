package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.data.CommonTestData;
import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FireStationServiceTest {

    @Autowired
    private FireStationService fireStationService;

    @Test
    void saveFireStationTest() {
        assertThat(fireStationService.save(CommonTestData.getFireStationToAdd())).isEqualTo(CommonTestData.getFireStationToAdd());
    }

    @Test
    void saveNullThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> fireStationService.save(null));
    }

    @Test
    void saveEmptyFireStationThrowExceptionTest() {
        assertThrows(ConstraintViolationException.class, () -> fireStationService.save(CommonTestData.getEmptyFireStation()));
    }

    @Test
    void updateFireStationTest() {
        assertThat(fireStationService.update(CommonTestData.getFireStationToUpdate())).isEqualTo(CommonTestData.getFireStationToUpdate());
    }

    @Test
    void updateNullFireStationThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> fireStationService.update(null));
    }

    @Test
    void deleteByFirstNameAndLastNameThrowsErrorTest() {
        FireStation fireStation = CommonTestData.getEmptyFireStation();
        assertThrows(NotFoundException.class, () -> fireStationService.delete(fireStation));
    }
}