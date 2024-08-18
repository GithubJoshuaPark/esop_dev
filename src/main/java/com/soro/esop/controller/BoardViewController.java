package com.soro.esop.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soro.esop.Validator.BoardValidator;
import com.soro.esop.dto.BoardDto;
import com.soro.esop.dto.UserDto;
import com.soro.esop.entiry.Board;
import com.soro.esop.entiry.User;
import com.soro.esop.mapper.BoardMapper;
import com.soro.esop.mapper.UserMapper;
import com.soro.esop.repository.nativeInterface.BoardWithUserDto;
import com.soro.esop.service.BoardService;
import com.soro.esop.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-17 16:52:28
 * @modify date : 2024-08-17 16:52:28
 * @desc : [Routes.java] 
 */

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService   boardService;
    private final UserService    userService;    
    private final BoardValidator boardValidator;

    @GetMapping("/list")
    public String boardList(@RequestParam(name="titleOrContent", required = false, defaultValue = "") String titleOrContent,
                            @PageableDefault(size = 5) Pageable pageable,
                            Model model) 
    {
        log.debug("pageable      : {}", pageable);
        log.debug("titleOrContent: {}", titleOrContent);

        Page<Board> boardList = null;
        Page<BoardWithUserDto> boardWithUserList = null;

        boardList         = boardService.findByTitleOrContent(titleOrContent, titleOrContent, pageable);
        boardWithUserList = boardService.findBoardsWithUsernamesByKeyword(titleOrContent, pageable);

        log.debug("boards        : {}", boardList);
        log.debug("boardsWithUser: {}", boardWithUserList);

        // convert Page<Board> to Page<BoardDto>
        // map method is feature of page interface will convert each element of the list
        // BoardMapper::toDto equivalent to board -> BoardMapper.toDto(board)    
        Page<BoardDto> boards_ = (boardList != null) ? boardList.map(BoardMapper::toDto) : null;

        int startPage_ = 1;
        int endPage_   = 1;
        int currentPage_ = 0;

        if(boards_ != null) {
            startPage_ = Math.max(boards_.getPageable().getPageNumber() - 4,1); // at least 1
            endPage_   = Math.min(boards_.getTotalPages(), boards_.getPageable().getPageNumber() + 4); // at most totalPages
            currentPage_ = boards_.getPageable().getPageNumber(); // 0-based index
        }
        
        log.debug("startPage: {}, endPage: {}, currentPage: {}", 
                          startPage_, endPage_, currentPage_ );
 
        model.addAttribute("startPage"  , startPage_      );
        model.addAttribute("endPage"    , endPage_        );
        model.addAttribute("pageSize"   , 5);
        model.addAttribute("currentPage", currentPage_    );
        model.addAttribute("boards"     , boards_          );
        return "board/list";
    }

    @GetMapping("/form")
    public String boardForm(@RequestParam(name="id", required = false, defaultValue = "") Long id,
                            Model model)
    {
        log.debug("id: {}", id);

        if(id != null && id > 0) {
            Board board = boardService.findById(id);
            log.debug("board: {}", board);

            BoardDto boardDto = BoardMapper.toDto(board);
            log.debug("boardDto: {}", boardDto);

            model.addAttribute("board", boardDto);
        }
        else {
            model.addAttribute("board", new BoardDto());
        }

        List<User> userList = userService.findAll();
        log.debug("users: {}", userList);
        List<UserDto> users = UserMapper.toDto(userList);

        // Add this line to provide the list of users
        model.addAttribute("users", users);

        return "board/form";
    }

    @PostMapping("/form")
    public String boardForm(@Valid @ModelAttribute("board") BoardDto boardDto, 
                            BindingResult result,
                            Model model) 
    {
        log.debug("boardDto: {}", boardDto);

        boardValidator.validate(boardDto, result);

        if(result.hasErrors()) {
            log.debug("errors: {}", result.getAllErrors());

            // No need to add "board" to the model again, @ModelAttribute takes care of it
            model.addAttribute("users", UserMapper.toDto(userService.findAll()));
            return "board/form";
        }

        // Get the username of the logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();
        log.debug("username: {}", username);

        // get user_id from the username
        User user = userService.findByUsername(username);
        log.debug("user: {}", user);

        // set the user_id to the boardDto, convert user_id to Long
        boardDto.setWriter(user.getId());
        

        Board board_ = BoardMapper.toEntity(boardDto);
        log.debug("board: {}", board_);

        boardService.save(board_);
        return "redirect:/board/list";
    }

}
