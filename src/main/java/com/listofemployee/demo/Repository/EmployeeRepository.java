package com.listofemployee.demo.Repository;

import com.listofemployee.demo.Model.Employee;
import com.listofemployee.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface extends JpaRepository and provides additional functionality for querying Employee entities.
 * It is annotated with @Repository to indicate it's a repository layer component.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    /**
     * Finds all employees associated with a given user.
     *
     * @param user an optional User object
     * @return a list of Employee entities or an empty list if no employees are found for the user
     */
    List<Employee> findByUser(Optional<User> user);


}
