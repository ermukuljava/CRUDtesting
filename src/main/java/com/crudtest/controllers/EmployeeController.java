package com.crudtest.controllers;

import com.crudtest.entities.Employee;
import com.crudtest.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody Employee employee){
        Optional<String> response = employeeService.createEmployee(employee);
        return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
    }
    @GetMapping(path = "/allEmployees", produces = "application/json")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> allEmployee = employeeService.getAllEmployee();
        return new ResponseEntity<>(allEmployee, HttpStatus.OK);
    }
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employeeById = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeById, HttpStatus.FOUND);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee){
        String response = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        String response = employeeService.deleteEmployee(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
