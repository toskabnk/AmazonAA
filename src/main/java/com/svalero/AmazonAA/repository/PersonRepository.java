package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findAll();
    List<Person> findByName(String name);
    Person findByUsername(String username);
}
