package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.data.CommonTestData;
import com.safetynet.safetynetsystem.dto.ChildAlertDTO;
import com.safetynet.safetynetsystem.dto.FireResponseDTO;
import com.safetynet.safetynetsystem.dto.StationCoverageDTO;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SafetyNetServiceTest {

    @Autowired
    private SafetyNetService safetyNetService;

    @Test
    void getPersonByStationNumber() {
        StationCoverageDTO stationCoverageExpected = CommonTestData.getPersonByStationNumber();
        StationCoverageDTO stationCoverageActual = safetyNetService.getPersonByStationNumber("4");
        assertThat(stationCoverageActual).isEqualTo(stationCoverageExpected);
    }

    @Test
    void getChildAlert() {
        List<ChildAlertDTO> actual = safetyNetService.getChildAlert("892 Downing Ct");
        List<ChildAlertDTO> expected = CommonTestData.getChildAlert();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getPhoneNumbersByFireStationTest() {
        List<String> actual = safetyNetService.getPhoneNumbersByFireStation("4");
        List<String> expected = CommonTestData.getPhoneNumbersByFireStation();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getPersonByAddress() {
        List<FireResponseDTO> actual = safetyNetService.getPersonByAddress("892 Downing Ct");
        List<FireResponseDTO> expected = CommonTestData.getPersonByAddress();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getHouseholdByFireStation() {
    }

    @Test
    void getPersonInfo() {
    }

    @Test
    void getCommunityEmailTest() {
        assertThat(safetyNetService.getCommunityEmail("Culver")).isEqualTo(CommonTestData.getCommunityEmails());
    }
}