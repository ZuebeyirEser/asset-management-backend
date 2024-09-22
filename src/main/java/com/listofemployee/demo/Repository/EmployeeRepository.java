package com.listofemployee.demo.Repository;

import com.listofemployee.demo.Model.Employee;
import com.listofemployee.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByUser(Optional<User> user);
}
