package com.soro.esop.service.serviceImpl;

import java.util.List;

import com.soro.esop.entiry.Board;
import com.soro.esop.repository.BoardRepository;
import com.soro.esop.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    
    private final BoardRepository boardRepository;

    @Override
    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Override
    public List<Board> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.isEmpty() ? null : boards;
    }

    @Override
    public Board save(Board Board) {
        return boardRepository.save(Board);
    }

    @Override
    public Board update(Board Board) {
        return boardRepository.save(Board);
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    
}
