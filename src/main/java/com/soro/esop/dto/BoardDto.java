package com.soro.esop.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min=2, max=30, message = "Title must be between 2 and 30 characters")
    private String title;
    
    @NotBlank(message = "Content를 입력해주세요.")
    private String content;

    @NotBlank(message = "Writer를 입력해주세요.")
    private String writer;

    private Boolean enabled = true;
    private LocalDate regDate = LocalDate.now();
}
