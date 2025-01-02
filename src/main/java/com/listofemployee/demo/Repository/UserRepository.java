package com.listofemployee.demo.Repository;

import com.listofemployee.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This interface extends JpaRepository and provides additional functionality for querying User entities.
 * It is used for data access operations on User entities in the database.
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user
     * @return an Optional containing the User object if found, or Optional.empty() if not found
     */
    Optional<User> findByEmail(String email);


}
