package com.azad.authservicemongodb.repositories;

import com.azad.authservicemongodb.models.ERole;
import com.azad.authservicemongodb.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(ERole name);
}
