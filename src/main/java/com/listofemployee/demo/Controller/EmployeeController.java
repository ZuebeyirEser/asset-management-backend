package com.listofemployee.demo.Controller;

import com.listofemployee.demo.Exceptions.ResourceNotFoundExceptions;
import com.listofemployee.demo.Model.Asset;
import com.listofemployee.demo.Model.Employee;
import com.listofemployee.demo.Model.User;
import com.listofemployee.demo.Repository.AssetRepository;
import com.listofemployee.demo.Repository.EmployeeRepository;
import com.listofemployee.demo.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class is a Spring REST controller that handles CRUD operations for employees.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for dependency injection.
     *
     * @param employeeRepository the employee repository
     * @param userRepository the user repository
     */
    public EmployeeController(EmployeeRepository employeeRepository, UserRepository userRepository ) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;

    }

    /**
     * Gets all employees for the authenticated user.
     *
     * @return a ResponseEntity containing a list of employees or an appropriate error message
     */
    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        //authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User mot found: " + email)));
        List<Employee> employees = employeeRepository.findByUser(user);
        return ResponseEntity.ok(employees);
    }

    /**
     * Creates a new employee for the authenticated user.
     *
     * @param employee the employee data to be created
     * @return the created employee
     */
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User mot found: " + email));
        employee.setUser(user);
        return employeeRepository.save(employee);
    }

    /**
     * Updates an employee for the authenticated user.
     *
     * @param id the ID of the employee to update
     * @param employeeDetails the updated employee data
     * @return a ResponseEntity containing a success message and the updated employee or an appropriate error message
     */
    @PutMapping("employees/{id}")
    public ResponseEntity<?>  updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found: " + email));

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User mot found: " + email));

        if (!employee.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You dont have permission to update this employee");
        }

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        Employee updatedEmployee = employeeRepository.save(employee);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee updated successfully");
        response.put("employee", updatedEmployee);

        return ResponseEntity.ok(response);

    }
    @DeleteMapping("employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

}
