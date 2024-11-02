package com.soro.esop.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "dx_biz_unit")
public class DxBizUnit {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;     // 고유 ID

    private String coCd;   // 회사코드
    private String bsCd;   // 사업장코드
    private String bsNm;   // 사업장명

    @NotBlank(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotBlank(message = "Address should not be empty")
    private String address;

    @NotBlank(message = "Email should snot be empty")
    @Email(message = "Email should be valid")
    private String email;

    private String description;
}
