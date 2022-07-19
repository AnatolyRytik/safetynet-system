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

    public Person update(Person person, Integer id) {
        Assert.notNull(person, "person must not be null");
        return checkNotFoundWithId(personRepository.save(person), id);
    }

    public void deleteById(Integer id) {
        checkNotFoundWithId(personRepository.findById(id), id);
        personRepository.deleteById(id);
    }

}
