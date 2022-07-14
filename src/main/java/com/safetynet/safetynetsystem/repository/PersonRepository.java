package com.safetynet.safetynetsystem.repository;

import com.safetynet.safetynetsystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
