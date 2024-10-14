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


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    // dependency injection
    public EmployeeController(EmployeeRepository employeeRepository, UserRepository userRepository ) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;

    }

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

    //create REST API
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User mot found: " + email));
        employee.setUser(user);
        return employeeRepository.save(employee);
    }


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
