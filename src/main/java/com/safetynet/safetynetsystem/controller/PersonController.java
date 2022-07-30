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

    @PostMapping(value = "/person", produces = "application/json")
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        ValidationUtil.checkNew(person);
        Person created = personService.save(person);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
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

    @GetMapping("/person/address")
    public ResponseEntity<List<Person>> getByAddress(@RequestParam String address) {
        List<Person> personList;
        try {
            personList = personService.getByAdress(address);
        } catch (NotFoundException e) {
            //log
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Person>>(personList, HttpStatus.OK);
    }

}
