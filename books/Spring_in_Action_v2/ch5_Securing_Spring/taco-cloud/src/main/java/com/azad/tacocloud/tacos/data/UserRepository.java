package com.azad.tacocloud.tacos.data;

import com.azad.tacocloud.tacos.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    /***
     * In addition to the CRUD operations provided by extending CrudRepository, UserRepository defines a
     * findByUsername() method that we'll use in the user details service to look up a User by their username.
     * @param username to look up.
     * @return The user with the username provided.
     */
    User findByUsername(String username);
}
