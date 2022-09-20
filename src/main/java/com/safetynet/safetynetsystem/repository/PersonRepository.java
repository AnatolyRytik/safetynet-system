package com.safetynet.safetynetsystem.repository;

import com.safetynet.safetynetsystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

    List<Person> findByAddress(String address);

    List<Person> findByLastName(String lastName);

    List<Person> findByCity(String city);
}
