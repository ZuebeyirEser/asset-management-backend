package com.listofemployee;

import com.listofemployee.demo.Controller.EmployeeController;
import com.listofemployee.demo.Model.Employee;
import com.listofemployee.demo.Model.User;
import com.listofemployee.demo.Repository.EmployeeRepository;
import com.listofemployee.demo.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setup() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testGetAllEmployees() {
        // Arrange
        String mockEmail = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(mockEmail);

        List<Employee> mockEmployeeList = Arrays.asList(
                new Employee("Employee 1"),
                new Employee("Employee 2")
        );

        when(authentication.getName()).thenReturn(mockEmail);
        when(userRepository.findByEmail(mockEmail)).thenReturn(Optional.of(mockUser));
        when(employeeRepository.findByUser(Optional.of(mockUser))).thenReturn(mockEmployeeList);

        // Act
        List<Employee> result = (List<Employee>) employeeController.getAllEmployees();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Employee 1", result.get(0).getFirstName());
        assertEquals("Employee 2", result.get(1).getFirstName());
        verify(userRepository, times(1)).findByEmail(mockEmail);
        verify(employeeRepository, times(1)).findByUser(Optional.of(mockUser));
    }
}
