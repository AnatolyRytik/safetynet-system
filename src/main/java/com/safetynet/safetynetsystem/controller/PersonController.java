package com.safetynet.safetynetsystem.controller;

import com.safetynet.safetynetsystem.model.Person;
import com.safetynet.safetynetsystem.service.PersonService;
import com.safetynet.safetynetsystem.util.ValidationUtil;
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
    public ResponseEntity<List> getAll() {
        return ResponseEntity.ok()
                .body(personService.getAll());
    }

    @GetMapping(value = "/person/{id}", produces = "application/json")
    public ResponseEntity<Person> getById(@PathVariable("id") Integer id) {
        Person person = personService.getById(id);
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
        return new ResponseEntity<>(personService.update(person, id), HttpStatus.OK);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Person> deleteById(@PathVariable("id") Integer id) {
        personService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
