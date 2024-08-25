package com.soro.esop.entity;

//import com.querydsl.core.annotations.Generated;
import com.soro.esop.entity.pk.ScorePk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ScorePk.class)
@Table(name = "score")
public class Score {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="score_id" , nullable=false )
  private Long scoreId;

  @Id @Column(name="student_id" , nullable=false ) private Long studentId;
  private Integer score;
}
