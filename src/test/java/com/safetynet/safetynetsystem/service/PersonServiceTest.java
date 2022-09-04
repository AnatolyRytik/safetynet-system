package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.data.CommonTestData;
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
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    void saveByFirstNameAndLastNameTest() {
        assertThat(personService.save(CommonTestData.getPersonToAdd())).isEqualTo(CommonTestData.getPersonToAdd());
    }

    @Test
    void saveNullThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> personService.save(null));
    }

    @Test
    void saveEmptyPersonThrowExceptionTest() {
        assertThrows(ConstraintViolationException.class, () -> personService.save(CommonTestData.getEmptyPerson()));
    }

    @Test
    void updateByFirstNameAndLastNameTest() {
        assertThat(personService.updateByFirstNameAndLastName(CommonTestData.getPersonToUpdate(), "John", "Boyd")).isEqualTo(CommonTestData.getPersonToUpdate());
    }

    @Test
    void updateNullPersonThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> personService.updateByFirstNameAndLastName(null, "Toto", "Tata"));
    }

    @Test
    void updateByWrongFirstNameAndLastNameThrowExceptionTest() {
        assertThrows(NotFoundException.class, () -> personService.updateByFirstNameAndLastName(CommonTestData.getPersonToUpdate(), "Toto", "Tata"));
    }

    @Test
    void deleteByFirstNameAndLastNameThrowsErrorTest() {
        assertThrows(NotFoundException.class, () -> personService.deleteByFirstNameAndLastName("Toto", "Tata"));
    }
}