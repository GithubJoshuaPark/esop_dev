package com.soro.esop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soro.esop.entiry.Board;
import com.soro.esop.repository.nativeInterface.BoardWithUserDto;

/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-17 17:18:23
 * @modify date : 2024-08-17 17:18:23
 * @desc [Board CRUD Repository]
 */
public interface BoardRepository extends JpaRepository<Board, Long> {    
    
    List<Board> findByTitle(String title);

    // This method will create a query like: SELECT * FROM board WHERE title LIKE ?1
    List<Board> findByTitleStartingWith(String titlePrefix);

    // This method will create a query like: SELECT * FROM board WHERE content LIKE ?1
    List<Board> findByContentStartingWith(String contentPrefix);

    // This method will create a query like: SELECT * FROM board WHERE title LIKE ?1 or content LIKE ?1
    List<Board> findByTitleStartingWithOrContentStartingWith(String title, String content);

    // This method will create a query like: SELECT * FROM board WHERE title LIKE ?1 or content LIKE ?2
    Page<Board> findByTitleStartingWithOrContentStartingWith(String title, String content, Pageable pageable);

    // This method will create a query like: SELECT * FROM board WHERE title LIKE %?1% or content LIKE %?2%
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    @Query(value = "SELECT b.id, b.title, b.content, b.writer, u.username, u.enabled " +
               "FROM board b " +
               "INNER JOIN user u ON b.writer = u.id " +
               "WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%", 
       countQuery = "SELECT COUNT(*) FROM board b INNER JOIN user u ON b.writer = u.id " +
                    "WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%",
       nativeQuery = true)
    Page<BoardWithUserDto> findBoardsWithUsernamesByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
}
