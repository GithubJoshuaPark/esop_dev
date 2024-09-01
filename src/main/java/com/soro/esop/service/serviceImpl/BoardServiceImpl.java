package com.soro.esop.service.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.soro.esop.entity.Board;
import com.soro.esop.repository.BoardRepository;
import com.soro.esop.repository.nativeQueryRsDto.BoardWithUserDto;
import com.soro.esop.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    
    private final BoardRepository boardRepository;

    @Override
    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Board> findAll(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        return boards.isEmpty() ? null : boards;
    }

    @Override
    public List<Board> findByTitle(String title) {
        List<Board> boards = boardRepository.findByTitleStartingWith(title);
        return boards.isEmpty() ? null : boards;
    }

    @Override
    public List<Board> findByContent(String content) {
        List<Board> boards = boardRepository.findByContentStartingWith(content);
        return boards.isEmpty() ? null : boards;
    }

    @Override
    public List<Board> findByTitleOrContent(String title, String content) {
        List<Board> boards = boardRepository.findByTitleStartingWithOrContentStartingWith(title, content);
        return boards.isEmpty() ? null : boards;
    }

    @Override
    public Page<Board> findByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
        return boards.isEmpty() ? null : boards;
    }

    @Override
    public Page<BoardWithUserDto> findBoardsWithUsernamesByKeyword(String keyword, Pageable pageable) {
        Page<BoardWithUserDto> boards = boardRepository.findBoardsWithUsernamesByKeyword(keyword, pageable);
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
