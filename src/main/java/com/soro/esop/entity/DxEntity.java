package com.soro.esop.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dx_entity")
public class DxEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String value;
}
