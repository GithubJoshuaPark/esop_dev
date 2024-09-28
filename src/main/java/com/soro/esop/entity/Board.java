package com.soro.esop.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min=2, max=30, message = "Title must be between 2 and 30 characters")
    private String title;

    @NotBlank(message = "Content를 입력해주세요.")
    private String content;

    private Long writer;

    private Boolean enabled = true;
    private LocalDate regDate = LocalDate.now();
}
