package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.service.SafetyNetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SafetyNetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SafetyNetService safetyNetService;

    @Test
    void getPersonsByStationNumber() throws Exception {
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getPersonsByStationNumberBadRequest() throws Exception {
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "8"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getChildAlert() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .param("address", "892 Downing Ct"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getChildAlertBadRequest() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .param("address", "toto"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getPhoneAlert() throws Exception {
        mockMvc.perform(get("/phoneAlert")
                        .param("station", "3"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getPhoneAlertBadRequest() throws Exception {
        mockMvc.perform(get("/phoneAlert")
                        .param("station", "8"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getPersonByAddress() throws Exception {
        mockMvc.perform(get("/fire")
                        .param("address", "112 Steppes Pl"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getPersonByAddressBadRequest() throws Exception {
        mockMvc.perform(get("/fire")
                        .param("address", "toto"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getHouseholdByFireStation() throws Exception {
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "3", "4"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getHouseholdByFireStationBadRequest() throws Exception {
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "9"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo")
                        .param("firstName", "Eric")
                        .param("lastName", "Cadigan"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getPersonInfoBadRequest() throws Exception {
        mockMvc.perform(get("/personInfo")
                        .param("firstName", "toto")
                        .param("lastName", "tata"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getCommunityEmail() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .param("city", "Culver"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getCommunityEmailBadRequest() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .param("city", "toto"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}