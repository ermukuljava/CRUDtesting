package com.crudtest.controllers;

import com.crudtest.entities.Employee;
import com.crudtest.services.Impl.EmployeeServiceImpl;
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
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @Before
    public void setUp(){
        employee=new Employee();
        employee.setEmployeeId(1L);
        employee.setEmployeeName("Mukul");
        employee.setCity("Farrukhabad");
    }
    @Test
    public void createEmployeeTest() throws Exception{
        Mockito.when(employeeService.createEmployee(any(Employee.class))).thenReturn("Employee created successfully");
        mockMvc.perform(post("/v1/api/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"employeeId\":1,\"employeeName\":\"Mukul\",\"city\":\"Farrukhabad\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Employee created successfully"));
    }
    @Test
    public void testGetEmployeeById() throws Exception {
        Mockito.when(employeeService.getEmployeeById(anyLong())).thenReturn(employee);

        mockMvc.perform(get("/v1/api/employees/1"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.employeeName").value("Mukul"))
                .andExpect(jsonPath("$.city").value("Farrukhabad"));
    }
    @Test
    public void testUpdateEmployee() throws Exception {
        Mockito.when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn("Employee updated successfully");

        mockMvc.perform(put("/v1/api/employees/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"employeeName\":\"Mukul\",\"city\":\"Farrukhabad\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee updated successfully"));

    }
    @Test
    public void testDeleteEmployee() throws Exception {
        Mockito.when(employeeService.deleteEmployee(anyLong())).thenReturn("Employee deleted successfully");

        mockMvc.perform(delete("/v1/api/employees/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));
    }
}
