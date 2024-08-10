package com.crudtest.services;

import com.crudtest.entities.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {

    public String createDepartment(Department department);

    public List<Department> getAllDepartments();

    public Department getDepartmentById(Long id);

    public String updateDepartment(Long id, Department department);

    public String deleteDepartment(Long id);
}
