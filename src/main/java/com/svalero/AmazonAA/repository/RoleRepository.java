package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}