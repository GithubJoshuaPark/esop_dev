package com.soro.esop.controller.v1.Board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soro.esop.dto.BoardDto;
import com.soro.esop.entiry.Board;
import com.soro.esop.mapper.BoardMapper;
import com.soro.esop.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-17 16:52:48
 * @modify date : 2024-08-17 16:52:48
 * @desc [BoardController.java] 
 */


/**
 * 게시판 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    
    /**
     * 게시판 목록 조회
     * @return
     */
    @SuppressWarnings("deprecation")
    @GetMapping
    public ResponseEntity<List<BoardDto>> getAll(@RequestParam(required = false, defaultValue = "", name="titleOrContent") String titleOrContent, 
                                                 Pageable pageable) 
    {
        log.debug("titleOrContent: {}", titleOrContent);

        if(StringUtils.isEmpty(titleOrContent)) {
            Page<Board> boardList =  boardService.findAll(pageable);
            return ResponseEntity.ok(BoardMapper.toDto(boardList.getContent())); // 200 OK
        }
        else {
            List<Board> boardList =  boardService.findByTitleOrContent(titleOrContent, titleOrContent);
            return ResponseEntity.ok(BoardMapper.toDto(boardList));  // 200 OK
        }                
    }

    /**
     * 게시판 상세 조회
     * @param id
     * @return: ResponseEntity<BoardDto>
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getOne(@PathVariable(name="id") Long id) {
        Board board = boardService.findById(id);
        if(board == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(BoardMapper.toDto(board)); // 200 OK
    }

    /**
     * 게시판 등록
     * @param boardDto
     * @return: ResponseEntity<BoardDto>
     */
    @PostMapping
    public ResponseEntity<BoardDto> create(@RequestBody BoardDto boardDto) {
        Board board = BoardMapper.toEntity(boardDto);
        board = boardService.save(board);
        return ResponseEntity.status(HttpStatus.CREATED).body(BoardMapper.toDto(board)); // 201 Created
    }

    /**
     * 게시판 수정
     * @param id
     * @param boardDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<BoardDto> putMethodName(@PathVariable(name="id") String id, 
                                                  @RequestBody BoardDto boardDto) 
    {
        Board board = boardService.findById(Long.parseLong(id));
        if(board == null) {
            // insert
            board = BoardMapper.toEntity(boardDto);
        }
        else {
            // update
            board.setTitle(boardDto.getTitle());
            board.setContent(boardDto.getContent());
            board.setWriter(boardDto.getWriter());
            board.setEnabled(boardDto.getEnabled());
        }
        
        board = boardService.save(board);
        return ResponseEntity.ok(BoardMapper.toDto(board)); // 200 OK
    }

    /**
     * 게시판 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name="id") Long id) {        
        Board board = boardService.findById(id);
        if(board == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        boardService.delete(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
    
    
}
