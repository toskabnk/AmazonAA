package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Person;
import com.svalero.AmazonAA.domain.Review;
import com.svalero.AmazonAA.domain.dto.PersonDTO;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findById(long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public List<Person> findByName(String name) {
        return personRepository.findByName(name);
    }

    @Override
    public Person findByUserName(String username){
        return personRepository.findByUsername(username);
    }

    @Override
    public Person addPerson(PersonDTO personDTO) {
        Person newPerson = new Person();

        modelMapper.map(personDTO, newPerson);
        newPerson.setReviews(new ArrayList<>());

        return personRepository.save(newPerson);
    }

    @Override
    public void deletePerson(long id) throws PersonNotFoundException {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
    }

    @Override
    public Person modifyPerson(long id, Person newPerson) throws PersonNotFoundException {
        Person existingPerson = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        List<Review> reviews = existingPerson.getReviews();
        modelMapper.map(newPerson, existingPerson);
        existingPerson.setId(id);
        existingPerson.setReviews(reviews);
        return personRepository.save(existingPerson);
    }
}
