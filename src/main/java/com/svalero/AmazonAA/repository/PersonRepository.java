package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findAll();
    List<Person> findByName(String name);
    Person findByUsername(String username);
    List<Person> findByPhoneNumberEquals(String phoneNumber);
    @Query(value = "SELECT \"person\".* FROM \"person\"\n" +
            "JOIN \"order\" ON \"order\".\"customer_id\" = \"person\".\"id\"\n" +
            "WHERE \"order\".\"paid\" = false", nativeQuery = true)
    List<Person> findNotPaidPersons();
}
