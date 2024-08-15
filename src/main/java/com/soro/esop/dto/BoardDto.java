package com.soro.esop.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;

    private String title;
    private String content;
    private Boolean enabled;
    private String writer;
    private LocalDate regDate;
}
