package com.crudtest.services;

import com.crudtest.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    public String createEmployee(Employee employee);

    public List<Employee> getAllEmployee();

    public Employee getEmployeeById(Long id);

    public String updateEmployee(Long id, Employee employee);

    public String deleteEmployee(Long id);
}
