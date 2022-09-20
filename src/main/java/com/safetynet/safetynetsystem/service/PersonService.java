package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.repository.PersonRepository;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PersonService {
    private static final Logger log = LogManager.getLogger(PersonService.class);
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        Assert.notNull(person, "person must not be null");
        log.info("successful save Person by = {}", person);
        return personRepository.save(person);
    }

    public Person updateByFirstNameAndLastName(Person person, String firstName, String lastName) {
        Assert.notNull(person, "person must not be null");
        Person personUpdated = personRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new NotFoundException(
                ("Person not found")));
        personRepository.delete(personUpdated);
        return personRepository.save(person);
    }

    public void deleteByFirstNameAndLastName(String firstName, String lastName) throws RuntimeException {
        Person person = personRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException(("Person not found")));
        personRepository.delete(person);
    }
}
