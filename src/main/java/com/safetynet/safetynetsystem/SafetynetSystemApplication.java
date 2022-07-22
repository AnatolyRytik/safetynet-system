package com.safetynet.safetynetsystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetsystem.model.Firestation;
import com.safetynet.safetynetsystem.model.MedicalRecord;
import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.repository.FireStationRepository;
import com.safetynet.safetynetsystem.repository.MedicalRecordRepository;
import com.safetynet.safetynetsystem.repository.PersonRepository;
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
            TypeReference<Person> typeReference = new TypeReference<Person>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/data.json");
            try {
                jsonTree = mapper.readTree(inputStream);

                List<Person> persons = getListFromJson("persons", Person.class);
                personRepository.saveAll(persons);

                List<Firestation> fireStations = getListFromJson("firestations", Firestation.class);
                firestationRepository.saveAll(fireStations);

                List<MedicalRecord> medicalRecords = getListFromJson("medicalrecords", MedicalRecord.class);
                medicalRecordRepository.saveAll(medicalRecords);

                System.out.println("Data Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save data: " + e.getMessage());
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
