package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findAll();
    Person findById();
    List<Person> findByName();
}
