package com.crudtest.services;

import com.crudtest.entities.Department;
import com.crudtest.exceptions.ResourceNotFoundException;
import com.crudtest.repositories.DepartmentRepo;
import com.crudtest.services.Impl.DepartmentServiceImpl;
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
@PrepareForTest(DepartmentServiceImpl.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private DepartmentServiceImpl departmentServiceImpl;

    private Department department;


    @Before
    public void setup() {
        department = new Department();
        department.setDepartmentId(1L);
        department.setDepartmentName("IT Department");
    }

    @Test
    public void testCreateDepartment() {
        Mockito.when(departmentRepo.save(department)).thenReturn(department);

        String result = departmentServiceImpl.createDepartment(department);

        Mockito.verify(departmentRepo, Mockito.times(1)).save(department);
        Assert.assertEquals("Department created successfully", result);
    }

    @Test
    public void testGetDepartmentByIdSuccess() {
        Mockito.when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));

        Department result = departmentServiceImpl.getDepartmentById(1L);

        Mockito.verify(departmentRepo, Mockito.times(1)).findById(1L);
        Assert.assertEquals(department, result);
    }

    @Test
    public void testGetDepartmentByIdNotFound() {
        Mockito.when(departmentRepo.findById(1L)).thenReturn(Optional.empty());

        Assert.assertThrows(ResourceNotFoundException.class, () -> {
            departmentServiceImpl.getDepartmentById(1L);
        });

        Mockito.verify(departmentRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testGetAllDepartments() {
        List<Department> departmentList = Arrays.asList(department);
        PowerMockito.when(departmentRepo.findAll()).thenReturn(departmentList);

        List<Department> result = departmentServiceImpl.getAllDepartments();

        Mockito.verify(departmentRepo, Mockito.times(1)).findAll();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(department, result.get(0));
    }

    @Test
    public void testUpdateDepartment() {
        Department updatedDepartment = new Department();
        updatedDepartment.setDepartmentName("HR Department");

        Mockito.when(departmentRepo.findById(1L)).thenReturn(Optional.of(department));
        Mockito.when(departmentRepo.save(department)).thenReturn(department);

        String response = departmentServiceImpl.updateDepartment(1L, updatedDepartment);

        Mockito.verify(departmentRepo, Mockito.times(1)).findById(1L);
        Mockito.verify(departmentRepo, Mockito.times(1)).save(department);
        Assert.assertEquals("Department updated successfully", response);
        Assert.assertEquals("HR Department", department.getDepartmentName());
    }

    @Test
    public void testDeleteDepartment() {
        PowerMockito.doNothing().when(departmentRepo).deleteById(1L);

        String result = departmentServiceImpl.deleteDepartment(1L);

        Mockito.verify(departmentRepo, Mockito.times(1)).deleteById(1L);
        Assert.assertEquals("Department deleted successfully", result);
    }
}
