package com.crudtest.services.Impl;


import com.crudtest.entities.Department;
import com.crudtest.exceptions.ObjectNotFoundException;
import com.crudtest.exceptions.ResourceNotFoundException;
import com.crudtest.repositories.DepartmentRepo;
import com.crudtest.services.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepo departmentRepo;

    public DepartmentServiceImpl(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    @Override
    public Optional<String> createDepartment(Department department) {
        departmentRepo.save(department);
        return Optional.of("Department created successfully");
    }
    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }
    @Override
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }
    @Override
    public String updateDepartment(Long id, Department department) {
        try{
            Department oldDepartment = getDepartmentById(id);
            if(oldDepartment==null){
                log.error("OldDepartment object is null");
                throw new ObjectNotFoundException("OldDepartment object not found");
            }else {
                oldDepartment.setDepartmentName(department.getDepartmentName());
                oldDepartment.setEmployeeList(department.getEmployeeList());
                departmentRepo.save(oldDepartment);
                log.info("Department with id {} updated successfully", id);
                return "Department updated successfully";
            }
        }catch (ObjectNotFoundException exception){
            throw new ObjectNotFoundException("Not Found");
        }
    }
    @Override
    public String deleteDepartment(Long id) {
        departmentRepo.deleteById(id);
        log.info("Department with id {} deleted successfully", id);
        return "Department deleted successfully";
    }
}
