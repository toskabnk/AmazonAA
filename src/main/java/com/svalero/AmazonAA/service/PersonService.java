package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Person;
import com.svalero.AmazonAA.domain.dto.PersonDTO;
import com.svalero.AmazonAA.exception.PersonNotFoundException;

import java.util.List;

public interface PersonService {

    List<Person> findAll();
    Person findById(long id) throws PersonNotFoundException;

    List<Person> findByName(String name);

    Person findByUserName(String username);

    Person addPerson(PersonDTO personDTO);
    void deletePerson(long id) throws  PersonNotFoundException;
    Person modifyPerson(long id, Person newPerson) throws PersonNotFoundException;

}
