package com.example.companyemployeespring.entity;

import com.example.companyemployeespring.model.EmployeePosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private int salary;
    @Enumerated(EnumType.STRING)
    private EmployeePosition position;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private String profilePic;
    private String password;
}
