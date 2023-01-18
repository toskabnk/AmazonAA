package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.Person;
import com.svalero.AmazonAA.domain.Review;
import com.svalero.AmazonAA.domain.dto.PersonDTO;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.repository.OrderRepository;
import com.svalero.AmazonAA.repository.PersonRepository;
import com.svalero.AmazonAA.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findById(long id) throws PersonNotFoundException {
        logger.info("ID Person: " + id);
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public List<Person> findByName(String name) {
        logger.info("Name Person: " + name);
        return personRepository.findByName(name);
    }

    @Override
    public Person findByUserName(String username){
        logger.info("Username Person: " + username);
        return personRepository.findByUsername(username);
    }

    @Override
    public List<Person> findByPhoneNumber(String phoneNumber) {
        logger.info("PhoneNumber Person: " + phoneNumber);
        return personRepository.findByPhoneNumberEquals(phoneNumber);
    }

    @Override
    public Person addPerson(PersonDTO personDTO) {
        logger.info("Person added: " + personDTO);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Person newPerson = new Person();

        modelMapper.map(personDTO, newPerson);
        newPerson.setReviews(new ArrayList<>());
        newPerson.setPassword(bCryptPasswordEncoder.encode(personDTO.getPassword()));

        return personRepository.save(newPerson);
    }

    @Override
    public boolean deletePerson(long id) throws PersonNotFoundException {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        List<Order> order = orderRepository.findByCustomer_Id(person.getId());
        List<Review> review = reviewRepository.findByCustomerReview(person);

        if(!order.isEmpty() || !review.isEmpty()){
            logger.error("Person " + person + " no se puede borrar ya que referencia a otras tablas en la base de datos, elimina esos datos antes.");
            return false;
        }
        logger.info("Deleted person:" + person);
        personRepository.delete(person);
        return true;
    }

    @Override
    public Person modifyPerson(long id, Person newPerson) throws PersonNotFoundException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Person existingPerson = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        logger.info("Existing Person: " + existingPerson);
        logger.info("New Person " + newPerson);
        List<Review> reviews = existingPerson.getReviews();
        modelMapper.map(newPerson, existingPerson);
        existingPerson.setPassword(bCryptPasswordEncoder.encode(newPerson.getPassword()));
        existingPerson.setId(id);
        existingPerson.setReviews(reviews);
        return personRepository.save(existingPerson);
    }

    @Override
    public List<Person> findNotPaidPersons() {
        return personRepository.findNotPaidPersons();
    }
}
