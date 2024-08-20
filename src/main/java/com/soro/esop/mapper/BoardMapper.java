package com.soro.esop.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.soro.esop.entiry.Board;
import com.soro.esop.model.BoardDto;
import com.soro.esop.repository.nativeInterface.BoardWithUserDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BoardMapper {

    public static BoardDto toBoardWithUserDto(BoardWithUserDto boardWithUserDto) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(boardWithUserDto.getId());
        boardDto.setTitle(boardWithUserDto.getTitle());
        boardDto.setContent(boardWithUserDto.getContent());
        boardDto.setWriter(boardWithUserDto.getWriter());
        boardDto.setUsername(boardWithUserDto.getUsername());
        boardDto.setEnabled(boardWithUserDto.getEnabled());
        return boardDto;
    }
    
    public static List<BoardDto> toBoardWithUserDtoList(List<BoardWithUserDto> boardWithUserDtos) {
        if(boardWithUserDtos == null) {
            return null;
        }
        List<BoardDto> boardDtos = new ArrayList<>();
        for (BoardWithUserDto boardWithUserDto : boardWithUserDtos) {
            boardDtos.add(toBoardWithUserDto(boardWithUserDto));
        }
        return boardDtos;
    }
    
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
        if(boards == null) {
            return null;
        }
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boards) {
            boardDtos.add(toDto(board));
        }
        // log boardDtos
        log.debug("boardDtos: {}", boardDtos);
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
        if(boardDtos == null || boardDtos.isEmpty()) {
            return null;
        }
        List<Board> boards = new ArrayList<>();
        for (BoardDto boardDto : boardDtos) {
            boards.add(toEntity(boardDto));
        }
        return boards;
    }
}
