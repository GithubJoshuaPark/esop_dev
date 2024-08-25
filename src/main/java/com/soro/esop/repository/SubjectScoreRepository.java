package com.soro.esop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entity.Score;
import com.soro.esop.entity.pk.ScorePk;

public interface SubjectScoreRepository extends JpaRepository<Score, ScorePk> {

}
