package com.listofemployee.demo.Controller;

import com.listofemployee.demo.Exceptions.ResourceNotFoundExceptions;
import com.listofemployee.demo.Model.Employee;
import com.listofemployee.demo.Model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //create REST API
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return  employeeRepository.save(employee);
    }

    // get employee by id
    @GetMapping("employees/{id}")
    public ResponseEntity<Employee>  getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Employee does not exist with id:" + id)
        );
        return ResponseEntity.ok(employee);

    }



}
