package com.soro.esop.repository.customizedRepository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soro.esop.entity.QScore;
import com.soro.esop.entity.QStudent;
import com.soro.esop.model.SubjectScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

/**
 * packageName : com.soro.esop.repository.customizedRepository
 * fileName    : SubjectScoreRepositoryCustomImpl
 * author      : sorom
 * date        : 2024-08-26
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-26           sorom             new
 */

@RequiredArgsConstructor
public class SubjectScoreRepositoryCustomImpl implements SubjectScoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SubjectScoreDto> findScoreByStudent(Long id) {

        // select
        //          st.student_id
        //         ,st.name
        //         ,st.age
        //         ,sc.score
        //         ,sc.subject
        // from student st
        // inner join score sc
        // on st.student_id  = sc.student_id
        // where st.student_id = 1
        // order by st.student_id ;

        QStudent st = QStudent.student;
        QScore   sc = QScore.score1;

        JPAQuery<Tuple> query = queryFactory
                .select(
                        st.studentId
                        ,st.name
                        ,st.age
                        ,sc.score
                        ,sc.subject
                )
                .from(st)
                .join(sc)
                .on(st.studentId.eq(sc.studentId))
                .where(st.studentId.eq(id))
                .orderBy(st.studentId.asc());

        return query.fetchJoin().fetch().stream()
                .map(tuple -> SubjectScoreDto.builder()
                        .studentId(tuple.get(st.studentId))
                        .name(tuple.get(st.name))
                        .age(tuple.get(st.age))
                        .score(tuple.get(sc.score))
                        .subject(tuple.get(sc.subject))
                        .build())
                .toList();
    }

    @Override
    public Page<SubjectScoreDto> findScoreByStudent(Long id, Pageable pageable) {
        QStudent st = QStudent.student;
        QScore   sc = QScore.score1;

        JPAQuery<Tuple> query = queryFactory
                .select(
                        st.studentId
                        ,st.name
                        ,st.age
                        ,sc.score
                        ,sc.subject
                )
                .from(st)
                .join(sc)
                .on(st.studentId.eq(sc.studentId))
                .where(st.studentId.eq(id))
                .orderBy(st.studentId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<SubjectScoreDto> subjectScoreDtos = query.fetchJoin().fetch().stream()
                .map(tuple -> SubjectScoreDto.builder()
                        .studentId(tuple.get(st.studentId))
                        .name(tuple.get(st.name))
                        .age(tuple.get(st.age))
                        .score(tuple.get(sc.score))
                        .subject(tuple.get(sc.subject))
                        .build())
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(st.studentId)
                .from(st)
                .join(sc)
                .on(st.studentId.eq(sc.studentId))
                .where(st.studentId.eq(id));

        return PageableExecutionUtils.getPage(subjectScoreDtos, pageable, countQuery::fetchCount);
    }

}
