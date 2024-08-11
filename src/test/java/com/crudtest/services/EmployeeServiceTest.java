package com.crudtest.services;

import com.crudtest.entities.Department;
import com.crudtest.entities.Employee;
import com.crudtest.exceptions.ResourceNotFoundException;
import com.crudtest.repositories.EmployeeRepo;
import com.crudtest.services.Impl.EmployeeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(EmployeeServiceImpl.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepo employeeRepo;
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    private Employee employee;

    private Department department;

    @Before
    public void setUp() {
        employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setEmployeeName("Mukul Dubey");
        employee.setCity("Kanpur");

        department = new Department();
        department.setDepartmentId(1L);
        department.setDepartmentName("Engineering");
    }

    @Test
    public void testCreateEmployee(){
        Mockito.when(employeeRepo.save(employee)).thenReturn(employee);
        Optional<String> response = employeeServiceImpl.createEmployee(employee);
        Mockito.verify(employeeRepo, Mockito.times(1)).save(employee);
        Assert.assertEquals(Optional.of("Employee details created successfully"), response);
    }

    @Test
    public void testGetAllEmployee() {
        List<Employee> employeeList = Arrays.asList(employee);
        Mockito.when(employeeRepo.findAll()).thenReturn(employeeList);

        List<Employee> result = employeeServiceImpl.getAllEmployee();

        Mockito.verify(employeeRepo, Mockito.times(1)).findAll();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(employee, result.get(0));
    }

    @Test
    public void testGetEmployeeByIdSuccess() {
        Mockito.when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeServiceImpl.getEmployeeById(1L);

        Mockito.verify(employeeRepo, Mockito.times(1)).findById(1L);
        Assert.assertEquals(employee, result);
    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        Mockito.when(employeeRepo.findById(1L)).thenReturn(Optional.empty());

        Assert.assertThrows(ResourceNotFoundException.class, () -> {
            employeeServiceImpl.getEmployeeById(1L);
        });

        Mockito.verify(employeeRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testUpdateEmployee() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setEmployeeName("Jane Doe");
        updatedEmployee.setCity("Los Angeles");

        Mockito.when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepo.save(employee)).thenReturn(employee);

        String result = employeeServiceImpl.updateEmployee(1L, updatedEmployee);

        Mockito.verify(employeeRepo, Mockito.times(1)).findById(1L);
        Mockito.verify(employeeRepo, Mockito.times(1)).save(employee);
        Assert.assertEquals("Employee with Id : 1 updated successfully", result);
        Assert.assertEquals("Jane Doe", employee.getEmployeeName());
        Assert.assertEquals("Los Angeles", employee.getCity());
    }

    @Test
    public void testDeleteEmployee() {
        PowerMockito.doNothing().when(employeeRepo).deleteById(1L);

        String response = employeeServiceImpl.deleteEmployee(1L);

        Mockito.verify(employeeRepo, Mockito.times(1)).deleteById(1L);
        Assert.assertEquals("Employee with Id : 1 deleted successfully", response);
    }
}
