package com.safetynet.safetynetsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetsystem.data.CommonTestData;
import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.service.MedicalRecordService;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTest() throws Exception {
        String medicalRecordToAdd = objectMapper.writeValueAsString(CommonTestData.getMedicalRecordToAdd());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                        .param("firstName", "Nickolas")
                        .param("lastName", "Cage")
                        .content(medicalRecordToAdd)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MedicalRecord medicalRecordActual = objectMapper.readValue(result.getResponse().getContentAsString(), MedicalRecord.class);
        Assertions.assertThat(medicalRecordActual).isEqualTo(CommonTestData.getMedicalRecordToAdd());
    }

    @Test
    void createNullMedicalRecordThrowsExceptionTest() throws Exception {
        String medicalRecordToAdd = objectMapper.writeValueAsString(CommonTestData.getEmptyMedicalRecord());

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                        .content(medicalRecordToAdd)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateTest() throws Exception {
        String medicalRecordToUpdate = objectMapper.writeValueAsString(CommonTestData.getMedicalRecordToUpdate());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Boyd")
                        .content(medicalRecordToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MedicalRecord medicalRecordActual = objectMapper.readValue(result.getResponse().getContentAsString(), MedicalRecord.class);
        Assertions.assertThat(medicalRecordActual).isEqualTo(CommonTestData.getMedicalRecordToUpdate());
    }

    @Test
    void updateNullMedicalRecordThrowsExceptionTest() throws Exception {
        String medicalRecordToUpdate = objectMapper.writeValueAsString(CommonTestData.getEmptyMedicalRecord());

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                        .content(medicalRecordToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void deleteTest() throws Exception {
        String medicalRecordToDelete = objectMapper.writeValueAsString(CommonTestData.getMedicalRecordToDelete());

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Boyd")
                        .content(medicalRecordToDelete)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteEmptyMedicalRecordTest() throws Exception {
        String medicalRecordToDelete = objectMapper.writeValueAsString(CommonTestData.getNotExistingMedicalRecord());

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                        .param("firstName", "Toto")
                        .param("lastName", "Tata")
                        .content(medicalRecordToDelete)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}