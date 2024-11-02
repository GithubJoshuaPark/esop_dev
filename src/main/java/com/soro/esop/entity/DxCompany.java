package com.soro.esop.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "dx_company")
public class DxCompany {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;     // 고유 ID

    private String coCd;   // 회사코드
    private String coNm;   // 회사명

    @NotBlank(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotBlank(message = "Address should not be empty")
    private String address;

    @NotBlank(message = "BizNo should not be empty")
    @Size(min=10, max=10, message = "BizNo should have 10 characters")
    private String bizNo;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    private String description;
}
