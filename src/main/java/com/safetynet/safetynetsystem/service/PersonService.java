package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.repository.PersonRepository;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        Assert.notNull(person, "person must not be null");
        return personRepository.save(person);
    }

    public Person updateByFirstNameAndLastName(Person person, String firstName, String lastName) {
        Assert.notNull(person, "person must not be null");
        Person personUpdated = personRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new NotFoundException(
                ("Person not found")));
        personRepository.delete(personUpdated);
        return personRepository.save(person);
    }

    public void deleteByFirstNameAndLastName(String firstName, String lastName) {
        Person person = personRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new NotFoundException(
                ("Person not found")));
        personRepository.delete(person);
    }
}
