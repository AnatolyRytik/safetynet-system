package com.safetynet.safetynetsystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetsystem.model.FireStation;
import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.repository.FireStationRepository;
import com.safetynet.safetynetsystem.repository.MedicalRecordRepository;
import com.safetynet.safetynetsystem.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SafetynetSystemApplication {

    private static final Logger logger = LogManager.getLogger(SafetynetSystemApplication.class);
    private JsonNode jsonTree;
    private ObjectMapper mapper;

    public static void main(String[] args) {
        SpringApplication.run(SafetynetSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(PersonRepository personRepository, FireStationRepository firestationRepository, MedicalRecordRepository medicalRecordRepository) {
        return args -> {
            // read JSON and load json
            mapper = new ObjectMapper();
            TypeReference<Person> typeReference = new TypeReference<>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/data.json");
            try {
                jsonTree = mapper.readTree(inputStream);

                List<Person> persons = getListFromJson("persons", Person.class);
                personRepository.saveAll(persons);

                List<FireStation> fireStations = getListFromJson("firestations", FireStation.class);
                firestationRepository.saveAll(fireStations);

                List<MedicalRecord> medicalRecords = getListFromJson("medicalrecords", MedicalRecord.class);
                medicalRecordRepository.saveAll(medicalRecords);

                logger.info("All class entities have been created");
            } catch (IOException e) {
                logger.error("Unable populate entities: " + e.getMessage());
            }
        };
    }

    private <T> List<T> getListFromJson(String nodeName, Class<T> nodeClass) throws IOException {
        List list = new ArrayList<T>();
        for (JsonNode jsonNode : jsonTree.get(nodeName)) {
            list.add(mapper.treeToValue(jsonNode, nodeClass));
        }
        return list;
    }

}
