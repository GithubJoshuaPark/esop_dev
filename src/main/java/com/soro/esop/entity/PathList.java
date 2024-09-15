package com.soro.esop.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "path_list")
public class PathList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;
    private String comment;
}
