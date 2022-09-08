package com.safetynet.safetynetsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetsystem.data.CommonTestData;
import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.service.FireStationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createTest() throws Exception {
        String fireStationToAdd = objectMapper.writeValueAsString(CommonTestData.getFireStationToAdd());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .content(fireStationToAdd)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        FireStation fireStationActual = objectMapper.readValue(result.getResponse().getContentAsString(), FireStation.class);
        Assertions.assertThat(fireStationActual).isEqualTo(CommonTestData.getFireStationToAdd());
    }

    @Test
    void createNullFireStationThrowsExceptionTest() throws Exception {
        String fireStationToAdd = objectMapper.writeValueAsString(CommonTestData.getEmptyFireStation());

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .content(fireStationToAdd)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateTest() throws Exception {
        String fireStationToUpdate = objectMapper.writeValueAsString(CommonTestData.getFireStationToUpdate());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .content(fireStationToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        FireStation fireStationActual = objectMapper.readValue(result.getResponse().getContentAsString(), FireStation.class);
        Assertions.assertThat(fireStationActual).isEqualTo(CommonTestData.getFireStationToUpdate());
    }

    @Test
    void updateNullFireStationThrowsExceptionTest() throws Exception {
        String fireStationToUpdate = objectMapper.writeValueAsString(CommonTestData.getEmptyFireStation());

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .content(fireStationToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void deleteTest() throws Exception {
        String fireStationToDelete = objectMapper.writeValueAsString(CommonTestData.getFireStationToAdd());

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                        .content(fireStationToDelete)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteEmptyFireStationTest() throws Exception {
        String fireStationToDelete = objectMapper.writeValueAsString(CommonTestData.getNotExistingFireStation());

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                        .content(fireStationToDelete)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}