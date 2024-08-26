package com.soro.esop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName : com.soro.esop.model
 * fileName    : SubjectScoreDto
 * author      : sorom
 * date        : 2024-08-26
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-26             sorom             new
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectScoreDto {
    private Long studentId;
    private String name;
    private Integer age;
    private String subject;
    private Integer score;
}
