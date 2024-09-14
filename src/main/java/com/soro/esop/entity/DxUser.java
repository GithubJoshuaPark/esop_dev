package com.soro.esop.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dx_user")
public class DxUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name should not be empty")
    @Size(min=3, message = "Name should have at least 3 characters")
    private String name;

    @NotBlank(message = "Value should not be empty")
    private String value;

    @NotBlank(message = "Address should not be empty")
    private String address;

    @NotBlank(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotBlank(message = "SSN should not be empty")
    @Size(min=13, max=13, message = "SSN should have 13 characters")
    private String ssn;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
}
