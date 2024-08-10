package com.crudtest.controllers;

import com.crudtest.entities.Department;
import com.crudtest.services.Impl.DepartmentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentServiceImpl departmentService;
    private Department department;

    @Before
    public void setUp(){
        department=new Department();
        department.setDepartmentId(1L);
        department.setDepartmentName("HRDept");
    }
    @Test
    public void createDepartmentTest() throws Exception{
        Mockito.when(departmentService.createDepartment(any(Department.class))).thenReturn("Department created successfully");
        mockMvc.perform(post("/v1/api/department/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"departmentId\":1,\"departmentName\":\"HRDept\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Employee created successfully"));
    }
    @Test
    public void testGetDepartmentById() throws Exception {
        Mockito.when(departmentService.getDepartmentById(anyLong())).thenReturn(department);

        mockMvc.perform(get("/v1/api/department/get/1"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.departmentName").value("HRDept"));
    }
    @Test
    public void testUpdateDepartment() throws Exception {
        Mockito.when(departmentService.updateDepartment(anyLong(), any(Department.class))).thenReturn("Department updated successfully");

        mockMvc.perform(put("/v1/api/department/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"departmentId\":1,\"departmentName\":\"HRDept\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee updated successfully"));

    }
    @Test
    public void testDeleteDepartment() throws Exception {
        Mockito.when(departmentService.deleteDepartment(anyLong())).thenReturn("Department deleted successfully");

        mockMvc.perform(delete("/v1/api/department/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Department deleted successfully"));
    }
}
