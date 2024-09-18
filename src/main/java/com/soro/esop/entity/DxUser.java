package com.soro.esop.entity;


import com.soro.esop.entity.listeners.DxUserListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "dx_user")
@EntityListeners(DxUserListener.class)
public class DxUser {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

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
