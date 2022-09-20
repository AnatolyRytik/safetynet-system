package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.service.PersonService;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PersonController {
    private static final Logger log = LogManager.getLogger(PersonController.class);
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/person", produces = "application/json")
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        log.info("create Person = {}", person);
        Person created = personService.save(person);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/person", produces = "application/json")
    public ResponseEntity updateByFirstNameAndLastName(@Valid @RequestBody Person person,
                                                       @RequestParam String firstName,
                                                       @RequestParam String lastName) {
        log.info("update Person with first name={} and last name = {} to = {}", firstName, lastName, person);
        Person personUpdated;
        try {
            personUpdated = personService.updateByFirstNameAndLastName(person, firstName, lastName);
        } catch (NotFoundException e) {
            log.error("Not found person with first name={} and last name = {}", firstName, lastName);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Person not found");
        }
        log.info("successful update Person = {}", person);
        return new ResponseEntity<>(personUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/person")
    public ResponseEntity deleteByFirstNameAndLastName(@RequestParam String firstName,
                                                       @RequestParam String lastName) {
        log.info("delete Person by first name={} and last name = {}", firstName, lastName);
        try {
            personService.deleteByFirstNameAndLastName(firstName, lastName);
        } catch (NotFoundException e) {
            log.error("Not found person with first name={} and last name = {}", firstName, lastName);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Person not found");
        }
        log.info("successful delete Person by first name={} and last name = {}", firstName, lastName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
