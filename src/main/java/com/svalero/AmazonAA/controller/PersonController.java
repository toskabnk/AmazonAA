package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Person;
import com.svalero.AmazonAA.domain.dto.PersonDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.service.OrderService;
import com.svalero.AmazonAA.service.PersonService;
import com.svalero.AmazonAA.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.svalero.AmazonAA.util.ErrorExceptionUtil.getErrorExceptionResponseEntity;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    private final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getPersons(@RequestParam Map<String,String> data){
        logger.info("GET Person");
        if (data.isEmpty()) {
            logger.info("END GET Person");
            return ResponseEntity.ok(personService.findAll());
        } else {
            if(data.containsKey("name")){
                List<Person> persons = personService.findByName(data.get("name"));
                logger.info("GET Person");
                return ResponseEntity.ok(persons);
            } else if(data.containsKey("username")){
                List<Person> persons = new ArrayList<>();
                persons.add(personService.findByUserName(data.get("username")));
                logger.info("GET Person");
                return ResponseEntity.ok(persons);
            } else if(data.containsKey("phoneNumber")){
                List<Person> persons = new ArrayList<>();
                persons = personService.findByPhoneNumber(data.get("phoneNumber"));
                logger.info("GET Person");
                return ResponseEntity.ok(persons);
            }
        }
        logger.error("Bad Request");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) throws PersonNotFoundException {
        logger.info("GET Person");
        Person person = personService.findById(id);
        logger.info("END GET Person");
        return ResponseEntity.ok(person);
    }

    @GetMapping("/persons/notPaid/")
    public ResponseEntity<List<Person>> getNotPaidPersons() {
        logger.info("GET Person");
        List<Person> person = personService.findNotPaidPersons();
        logger.info("END GET Person");
        return ResponseEntity.ok(person);
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> addPerson(@Valid @RequestBody PersonDTO personDTO){
        logger.info("POST Person");
        Person newPerson = personService.addPerson(personDTO);
        logger.info("END POST Person");
        return ResponseEntity.status(HttpStatus.OK).body(newPerson);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> modifyPerson(@PathVariable long id,@Valid @RequestBody Person person) throws PersonNotFoundException{
        logger.info("PUT Person");
        Person newPerson = personService.modifyPerson(id, person);
        logger.info("END PUT Person");
        return ResponseEntity.status(HttpStatus.OK).body(newPerson);
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<ErrorException> detelePerson(@PathVariable long id) throws PersonNotFoundException{
        logger.info("DELETE Person");

        boolean result = personService.deletePerson(id);
        logger.info("END DELETE Person");
        if(result){
            return ResponseEntity.noContent().build();
        } else {
            ErrorException error = new ErrorException(403, "El borrado no se ha permitido.");
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorException> handlePersonNotFoundException(PersonNotFoundException pnfe){
        logger.error("Person no encontrado");
        ErrorException errorException= new ErrorException(404, pnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorException> handleConstraintViolationException(ConstraintViolationException cve){
        logger.error("Restricciones violadas");
        return getErrorExceptionResponseEntity(cve);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException> handleException(Exception e){
        logger.error("Error Interno " + e.getMessage());
        ErrorException error = new ErrorException(500, "Ha ocurrido un error inesperado.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorException> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve){
        logger.error("Datos introducidos erroneos");
        return getErrorExceptionResponseEntity(manve);
    }

}