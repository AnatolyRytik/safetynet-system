package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.data.CommonTestData;
import com.safetynet.safetynetsystem.model.MedicalRecord;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class MedicalRecordServiceTest {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Test
    void saveMedicalRecordTest() {
        MedicalRecord medicalRecord = CommonTestData.getMedicalRecordToAdd();
        assertThat(medicalRecordService.save(medicalRecord)).isEqualTo(medicalRecord);
    }

    @Test
    void saveNullThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> medicalRecordService.save(null));
    }

    @Test
    void saveEmptyMedicalRecordThrowExceptionTest() {
        assertThrows(ConstraintViolationException.class, () -> medicalRecordService.save(CommonTestData.getEmptyMedicalRecord()));
    }

    @Test
    void updateByFirstNameAndLastNameTest() {
        MedicalRecord medicalRecord = CommonTestData.getMedicalRecordToUpdate();
        assertThat(medicalRecordService.updateByFirstNameAndLastName(medicalRecord, "John", "Boyd")).isEqualTo(medicalRecord);
    }

    @Test
    void updateNullMedicalRecordThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> medicalRecordService.updateByFirstNameAndLastName(null, "Toto", "Tata"));
    }

    @Test
    void updateByWrongFirstNameAndLastNameThrowExceptionTest() {
        assertThrows(NotFoundException.class, () -> medicalRecordService.updateByFirstNameAndLastName(CommonTestData.getMedicalRecordToUpdate(), "Toto", "Tata"));
    }

    @Test
    void deleteByFirstNameAndLastNameThrowsErrorTest() {
        assertThrows(NotFoundException.class, () -> medicalRecordService.deleteByFirstNameAndLastName("Toto", "Tata"));
    }

}