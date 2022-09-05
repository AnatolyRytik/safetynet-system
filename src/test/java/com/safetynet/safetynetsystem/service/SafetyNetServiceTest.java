package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.data.CommonTestData;
import com.safetynet.safetynetsystem.dto.*;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SafetyNetServiceTest {

    @Autowired
    private SafetyNetService safetyNetService;

    @Test
    void getPersonByStationNumberTest() {
        StationCoverageDTO stationCoverageExpected = CommonTestData.getPersonByStationNumber();
        StationCoverageDTO stationCoverageActual = safetyNetService.getPersonByStationNumber("4");
        assertThat(stationCoverageActual).isEqualTo(stationCoverageExpected);
    }

    @Test
    void getPersonByStationNumberNullThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> safetyNetService.getPersonByStationNumber(null));
    }

    @Test
    void getChildAlertTest() {
        List<ChildAlertDTO> actual = safetyNetService.getChildAlert("892 Downing Ct");
        List<ChildAlertDTO> expected = CommonTestData.getChildAlert();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getChildAlertNullThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> safetyNetService.getChildAlert(null));
    }

    @Test
    void getPhoneNumbersByFireStationTest() {
        List<String> actual = safetyNetService.getPhoneNumbersByFireStation("4");
        List<String> expected = CommonTestData.getPhoneNumbersByFireStation();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getPhoneNumbersByFireStationNullThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> safetyNetService.getPhoneNumbersByFireStation(null));
    }

    @Test
    void getPersonByAddressTest() {
        List<FireResponseDTO> actual = safetyNetService.getPersonByAddress("892 Downing Ct");
        List<FireResponseDTO> expected = CommonTestData.getPersonByAddress();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getPersonByAddressNullThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> safetyNetService.getPersonByAddress(null));
    }

    @Test
    void getHouseholdByFireStationTest() {
        List<FloodResponseDTO> actual = safetyNetService.getHouseholdByFireStation(List.of("2"));
        List<FloodResponseDTO> expected = CommonTestData.getHouseholdByFireStation();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getHouseholdByFireStationNullThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> safetyNetService.getHouseholdByFireStation(null));
    }

    @Test
    void getPersonInfoTest() {
        PersonInfoDTO actual = safetyNetService.getPersonInfo("Eric", "Cadigan");
        PersonInfoDTO expected = CommonTestData.getPersonInfo();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getPersonInfoNullThrowExceptionTest() {
        assertThrows(NotFoundException.class, () -> safetyNetService.getPersonInfo(null, null));
    }

    @Test
    void getCommunityEmailTest() {
        assertThat(safetyNetService.getCommunityEmail("Culver")).isEqualTo(CommonTestData.getCommunityEmails());
    }

    @Test
    void getCommunityEmailNullThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> safetyNetService.getCommunityEmail(null));
    }
}