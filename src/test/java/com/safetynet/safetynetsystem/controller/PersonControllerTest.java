package com.safetynet.safetynetsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetsystem.data.CommonTestData;
import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.service.PersonService;
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
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTest() throws Exception {
        String personToAdd = objectMapper.writeValueAsString(CommonTestData.getPersonToAdd());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .param("firstName", "Ericson")
                        .param("lastName", "Cadigans")
                        .content(personToAdd)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Person personActual = objectMapper.readValue(result.getResponse().getContentAsString(), Person.class);
        Assertions.assertThat(personActual).isEqualTo(CommonTestData.getPersonToAdd());
    }

    @Test
    void createNullPersonThrowsExceptionTest() throws Exception {
        String personToAdd = objectMapper.writeValueAsString(CommonTestData.getEmptyPerson());

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .content(personToAdd)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateTest() throws Exception {
        String personToUpdate = objectMapper.writeValueAsString(CommonTestData.getPersonToUpdate());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/person")
                        .param("firstName", "John")
                        .param("lastName", "Boyd")
                        .content(personToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Person personActual = objectMapper.readValue(result.getResponse().getContentAsString(), Person.class);
        Assertions.assertThat(personActual).isEqualTo(CommonTestData.getPersonToUpdate());
    }

    @Test
    void updateNullPersonThrowsExceptionTest() throws Exception {
        String personToUpdate = objectMapper.writeValueAsString(CommonTestData.getEmptyPerson());

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                        .content(personToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void deleteTest() throws Exception {
        String personToDelete = objectMapper.writeValueAsString(CommonTestData.getPersonToDelete());

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Boyd")
                        .content(personToDelete)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteEmptyPersonTest() throws Exception {
        String personToDelete = objectMapper.writeValueAsString(CommonTestData.getNotExistingPerson());

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                        .content(personToDelete)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}