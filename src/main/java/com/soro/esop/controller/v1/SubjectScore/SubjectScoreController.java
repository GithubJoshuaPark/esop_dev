package com.soro.esop.controller.v1.SubjectScore;

import com.soro.esop.model.SubjectScoreDto;
import com.soro.esop.service.SubjectScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName : com.soro.esop.controller.v1.SubjectScore
 * fileName    : SubjectScoreController
 * author      : sorom
 * date        : 2024-08-26
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-26             sorom             new
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/score/{id}")
@RequiredArgsConstructor
public class SubjectScoreController {

    private final SubjectScoreService service;

    @GetMapping
    public ResponseEntity<List<SubjectScoreDto>> getSubjectScoreList(@PathVariable(name="id") Long id) {

        log.debug("getSubjectScoreList : {}", id);
        List<SubjectScoreDto> subjectScoreList = service.findScoreByStudent(id);
        log.debug("subjectScoreList : {}", subjectScoreList);

        return ResponseEntity.ok(subjectScoreList);
    }
}
