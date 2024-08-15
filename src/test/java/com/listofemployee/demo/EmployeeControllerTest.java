package com.listofemployee.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.listofemployee.demo.Controller.EmployeeController;
import com.listofemployee.demo.Model.Employee;
import com.listofemployee.demo.Model.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testGetAllEmployees() throws Exception {
		// Arrange
		Employee employee = new Employee(1L, "John", "Doe", "john.doe@example.com");
		when(employeeRepository.findAll()).thenReturn(List.of(employee));

		// Act & Assert
		mockMvc.perform(get("/api/v1/employees"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(1))
				.andExpect(jsonPath("$[0].firstName").value("John"));
	}

	@Test
	void testCreateEmployee() throws Exception {
		// Arrange
		Employee employee = new Employee(1L, "John", "Doe", "john.doe@example.com");
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		// Act & Assert
		mockMvc.perform(post("/api/v1/employees")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John"));
	}

	@Test
	void testGetEmployeeById() throws Exception {
		// Arrange
		Employee employee = new Employee(1L, "John", "Doe", "john.doe@example.com");
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

		// Act & Assert
		mockMvc.perform(get("/api/v1/employees/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John"));
	}

	@Test
	void testGetEmployeeByIdNotFound() throws Exception {

		when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/v1/employees/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateEmployee() throws Exception {
		Employee existingEmployee = new Employee(1L, "John", "Doe", "john.doe@example.com");
		Employee updatedEmployee = new Employee(1L, "Jane", "Doe", "jane.doe@example.com");

		when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
		when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

		mockMvc.perform(put("/api/v1/employees/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedEmployee)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Jane"));
	}

	@Test
	void testUpdateEmployeeNotFound() throws Exception {

		Employee updatedEmployee = new Employee(1L, "Jane", "Doe", "jane.doe@example.com");

		when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

		mockMvc.perform(put("/api/v1/employees/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedEmployee)))
				.andExpect(status().isNotFound());
	}
}
