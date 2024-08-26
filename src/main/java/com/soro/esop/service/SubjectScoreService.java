package com.soro.esop.service;

import com.soro.esop.model.SubjectScoreDto;
import com.soro.esop.repository.SubjectScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : com.soro.esop.service
 * fileName    : SubjectScoreService
 * author      : sorom
 * date        : 2024-08-26
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-26             sorom             new
 */

@Service
@RequiredArgsConstructor
public class SubjectScoreService {

    private final SubjectScoreRepository repository;

    public List<SubjectScoreDto> findScoreByStudent(Long id) {
        return repository.findScoreByStudent(id);
    }
}
