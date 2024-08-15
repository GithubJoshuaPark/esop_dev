package com.soro.esop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soro.esop.entiry.Board;

@Service
public interface BoardService {
    Board findById(Long id);
    List<Board> findAll();
    Board save(Board Board);
    Board update(Board Board);
    void delete(Long id);
}

