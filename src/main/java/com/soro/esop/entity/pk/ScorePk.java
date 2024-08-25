package com.soro.esop.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ScorePk implements Serializable {
  @Id @Column(name="student_id", nullable=false) private Long studentId;
  @Id @Column(name="score_id", nullable=false) private Long scoreId;
}
