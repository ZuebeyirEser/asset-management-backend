package com.listofemployee.demo.Repository;

import com.listofemployee.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    // Find user by email
    Optional<User> findByEmail(String email);


}
