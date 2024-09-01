package com.soro.esop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soro.esop.entity.Board;
import com.soro.esop.repository.nativeQueryRsDto.BoardWithUserDto;


public interface BoardService {
    Board findById(Long id);
    Page<Board> findAll(Pageable pageable);
    List<Board> findByTitle(String title);
    List<Board> findByContent(String content);
    List<Board> findByTitleOrContent(String title, String content);
    Page<Board> findByTitleOrContent(String title, String content, Pageable pageable);
    
    // findBoardsWithUsernamesByKeyword
    Page<BoardWithUserDto> findBoardsWithUsernamesByKeyword(String keyword, Pageable pageable);

    Board save(Board Board);
    Board update(Board Board);
    void delete(Long id);
}

