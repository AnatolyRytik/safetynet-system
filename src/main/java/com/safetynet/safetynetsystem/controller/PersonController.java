package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.service.PersonService;
import com.safetynet.safetynetsystem.util.ValidationUtil;
import com.safetynet.safetynetsystem.util.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/person", produces = "application/json")
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok()
                .body(personService.getAll());
    }

    @GetMapping(value = "/person/{id}", produces = "application/json")
    public ResponseEntity<Person> getById(@PathVariable("id") Integer id) {
        Person person;
        try {
            person = personService.getById(id);
        } catch (NotFoundException e) {
            //log
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping(value = "/person", produces = "application/json")
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        ValidationUtil.checkNew(person);
        Person created = personService.save(person);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/person/{id}", produces = "application/json")
    public ResponseEntity<Person> updateById(@Valid @RequestBody Person person,
                                             @PathVariable("id") Integer id) {
        ValidationUtil.assureIdConsistent(person, id);
        Person personUpdated;
        try {
            personUpdated = personService.updateById(person, id);
        } catch (NotFoundException e) {
            //log
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(personUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Person> deleteById(@PathVariable("id") Integer id) {
        try {
            personService.deleteById(id);
        } catch (NotFoundException e) {
            //log
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/person")
    public ResponseEntity<Person> deleteByFirstNameAndLastName(@RequestParam String firstName,
                                                               @RequestParam String lastName) {
        try {
            personService.deleteByFirstNameAndLastName(firstName, lastName);
        } catch (NotFoundException e) {
            //log
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/person", produces = "application/json")
    public ResponseEntity<Person> updateByFirstNameAndLastName(@Valid @RequestBody Person person,
                                                               @RequestParam String firstName,
                                                               @RequestParam String lastName) {
        Person personUpdated;
        try {
            personUpdated = personService.updateByFirstNameAndLastName(person, firstName, lastName);
        } catch (NotFoundException e) {
            //log
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(personUpdated, HttpStatus.OK);
    }

}
