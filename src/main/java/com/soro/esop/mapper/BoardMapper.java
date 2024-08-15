package com.soro.esop.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.soro.esop.dto.BoardDto;
import com.soro.esop.entiry.Board;

@Component
public class BoardMapper {
    public static BoardDto toDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        boardDto.setWriter(board.getWriter());
        boardDto.setRegDate(board.getRegDate());
        return boardDto;
    }

    public static List<BoardDto> toDto(List<Board> boards) {
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boards) {
            boardDtos.add(toDto(board));
        }
        return boardDtos;
    }
    
    public static Board toEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setId(boardDto.getId());
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setWriter(boardDto.getWriter());
        board.setRegDate(boardDto.getRegDate());
        return board;
    }

    public static List<Board> toEntity(List<BoardDto> boardDtos) {
        List<Board> boards = new ArrayList<>();
        for (BoardDto boardDto : boardDtos) {
            boards.add(toEntity(boardDto));
        }
        return boards;
    }
}
