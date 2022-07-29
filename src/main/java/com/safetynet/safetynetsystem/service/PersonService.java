package com.safetynet.safetynetsystem.service;

import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.repository.PersonRepository;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.safetynet.safetynetsystem.util.ValidationUtil.checkNotFoundWithId;

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

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person getById(Integer id) {
        return personRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ("Person not found")));
    }

    public Person updateById(Person person, Integer id) {
        Assert.notNull(person, "person must not be null");
        personRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ("Person not found")));
        return personRepository.save(person);
    }

    public void deleteById(Integer id) {
        checkNotFoundWithId(personRepository.findById(id), id);
        personRepository.deleteById(id);
    }

    public Person updateByFirstNameAndLastName(Person person, String firstName, String lastName) {
        Assert.notNull(person, "person must not be null");
        Person foundPerson = personRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new NotFoundException(
                ("Person not found")));
        return personRepository.save(person);
    }

    public void deleteByFirstNameAndLastName(String firstName, String lastName) {
        Person person = personRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new NotFoundException(
                ("Person not found")));
        personRepository.delete(person);
    }

}
