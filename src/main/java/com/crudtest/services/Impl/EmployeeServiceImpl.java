package com.crudtest.services.Impl;

import com.crudtest.entities.Employee;
import com.crudtest.exceptions.ObjectNotFoundException;
import com.crudtest.exceptions.ResourceNotFoundException;
import com.crudtest.repositories.EmployeeRepo;
import com.crudtest.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo){
        this.employeeRepo=employeeRepo;
    }

    @Override
    public Optional<String> createEmployee(Employee employee) {
        employeeRepo.save(employee);
        log.info("Employee saved successfully");
        return Optional.of("Employee details created successfully");
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> allEmployee = employeeRepo.findAll();
        log.info("All employees lis delivered");
        return allEmployee;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with : " + id));
        log.info("Employee with {} found", id);
        return employee;
    }

    @Override
    public String updateEmployee(Long id, Employee employee) {
        try{
            Employee oldEmployee = getEmployeeById(id);
            if(oldEmployee==null){
                log.error("OldEmployee object is null");
                throw new ObjectNotFoundException("Old employee object is null");
            }else {
                oldEmployee.setEmployeeName(employee.getEmployeeName());
                oldEmployee.setCity(employee.getCity());
                oldEmployee.setDepartment(employee.getDepartment());
                employeeRepo.save(oldEmployee);
                log.info("Employee with id {} updated successfully", id);
                return "Employee with Id : "+id+" updated successfully";
            }
        } catch (ObjectNotFoundException ex){
            throw new ObjectNotFoundException("Employee object contains null value");
        }
    }

    @Override
    public String deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
        log.info("Employee with id {} deleted successfully", id);
        return "Employee with Id : "+id+" deleted successfully";
    }
}
