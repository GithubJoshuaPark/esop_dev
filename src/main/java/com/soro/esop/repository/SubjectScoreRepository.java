package com.soro.esop.repository;

import com.soro.esop.repository.queryDsl.SubjectScoreDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entity.Score;
import com.soro.esop.entity.pk.ScorePk;

public interface SubjectScoreRepository extends JpaRepository<Score, ScorePk>, SubjectScoreDslRepository {

}
