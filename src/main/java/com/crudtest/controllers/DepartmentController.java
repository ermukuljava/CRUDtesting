package com.crudtest.controllers;

import com.crudtest.entities.Department;
import com.crudtest.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/departments")
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService=departmentService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createDepartment(@Valid @RequestBody Department department){
        String response = departmentService.createDepartment(department);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id){
        Department department = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.FOUND);
    }
    @GetMapping(path = "/allDepartments", produces = "application/json")
    public ResponseEntity<List<Department>> getAllDepartments(){
        List<Department> allDepartments = departmentService.getAllDepartments();
        return new ResponseEntity<>(allDepartments, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @Valid @RequestBody Department department){
        String response = departmentService.updateDepartment(id, department);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        String response = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
