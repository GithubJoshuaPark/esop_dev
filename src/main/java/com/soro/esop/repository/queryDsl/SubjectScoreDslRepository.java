package com.soro.esop.repository.queryDsl;

import com.soro.esop.model.SubjectScoreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * packageName : com.soro.esop.repository.customizedRepository
 * fileName    : SubjectScoreRepositoryCustom
 * author      : sorom
 * date        : 2024-08-26
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-26             sorom             new
 */

public interface SubjectScoreDslRepository {
    List<SubjectScoreDto> findScoreByStudent(Long id);
    Page<SubjectScoreDto> findScoreByStudent(Long id, Pageable pageable);
}
