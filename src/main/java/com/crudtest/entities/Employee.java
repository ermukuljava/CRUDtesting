package com.crudtest.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotEmpty(message = "Employee name is mandatory")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Employee name can contain only letters and spaces")
    @Size(min = 4, max = 50)
    private String employeeName;

    @NotEmpty(message = "City name is mandatory")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "City name can contain only letters and spaces")
    @Size(min = 2, max = 50)
    private String city;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    @NotNull(message = "Department is mandatory")
    private Department department;
}
