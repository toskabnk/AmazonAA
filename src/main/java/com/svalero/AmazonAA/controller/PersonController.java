package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Person;
import com.svalero.AmazonAA.domain.dto.PersonDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getPersons(@RequestParam Map<String,String> data){
        if (data.isEmpty()) {
            return ResponseEntity.ok(personService.findAll());
        } else {
            if(data.containsKey("name")){
                List<Person> persons = personService.findByName(data.get("name"));
                return ResponseEntity.ok(persons);
            } else if(data.containsKey("username")){
                List<Person> persons = new ArrayList<>();
                persons.add(personService.findByUserName(data.get("username")));
                return ResponseEntity.ok(persons);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) throws PersonNotFoundException {
        Person person = personService.findById(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> addPerson(@Valid @RequestBody PersonDTO personDTO){
        Person newPerson = personService.addPerson(personDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newPerson);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> modifyPerson(@PathVariable long id, @RequestBody Person person) throws PersonNotFoundException{
        Person newPerson = personService.modifyPerson(id, person);
        return ResponseEntity.status(HttpStatus.OK).body(newPerson);
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Void> detelePerson(@PathVariable long id) throws PersonNotFoundException{
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorException> handlePersonNotFoundException(PersonNotFoundException pnfe){
        ErrorException errorException= new ErrorException(404, pnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorException> handleConstraintViolationException(ConstraintViolationException cve){
        Map<String, String> errors = new HashMap<>();
        cve.getConstraintViolations().forEach(error -> {
            String fieldName = error.getMessage();
            String name = error.toString();
            errors.put(fieldName, name);
        });
        ErrorException errorException = new ErrorException(500, "Internal Server Error", errors);
        return new ResponseEntity<>(errorException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException> handleException(Exception e){
        //Borrar el mensaje del error 500 luego
        ErrorException error = new ErrorException(500, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}