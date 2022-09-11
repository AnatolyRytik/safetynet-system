package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.service.PersonService;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/person", produces = "application/json")
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        log.info("create Person with first name={} and last name = {}", person.getFirstName(), person.getLastName());
        Person created = personService.save(person);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/person", produces = "application/json")
    public ResponseEntity updateByFirstNameAndLastName(@Valid @RequestBody Person person,
                                                       @RequestParam String firstName,
                                                       @RequestParam String lastName) {
        log.info("update Person with first name={} and last name = {}", firstName, lastName);
        Person personUpdated;
        try {
            personUpdated = personService.updateByFirstNameAndLastName(person, firstName, lastName);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Person not found");
        }
        return new ResponseEntity<>(personUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/person")
    public ResponseEntity deleteByFirstNameAndLastName(@RequestParam String firstName,
                                                       @RequestParam String lastName) {
        log.info("delete Person by first name={} and last name = {}", firstName, lastName);
        try {
            personService.deleteByFirstNameAndLastName(firstName, lastName);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Person not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
