package com.soro.esop.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.soro.esop.model.BoardDto;

import ch.qos.logback.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-17 16:52:48
 * @modify date : 2024-08-17 16:52:48
 * @desc [description]
 */

@Slf4j
@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(@SuppressWarnings("null") Class<?> clazz) {
        log.debug("clazz: {}", clazz);
        return clazz.isAssignableFrom(BoardDto.class);
    }

    @Override
    public void validate(@SuppressWarnings("null") Object target, 
                         @SuppressWarnings("null") Errors errors) {
        log.debug("target: {}", target);
        log.debug("errors: {}", errors);

        BoardDto board = (BoardDto) target;
        log.debug("board: {}", board);

        if (board.getTitle().length() < 2) {
            errors.rejectValue("title", "title.min.length", new Object[] {2}, "제목은 2자 이상 입력해주세요.");
        }

        if (board.getTitle().length() > 30) {
            errors.rejectValue("title", "title.max.length", new Object[] {30}, "제목은 30자 이하로 입력해주세요.");
        }

        if(StringUtil.isNullOrEmpty(board.getContent())) {
            errors.rejectValue("content", "content.empty", new Object[] {}, "내용을 입력해주세요.");
        }

        if (board.getContent().length() < 2) {
            errors.rejectValue("content", "content.min.length", new Object[] {2}, "내용은 2자 이상 입력해주세요.");
        }
    }

    
}
