package com.soro.esop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entiry.Board;

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
    
}
