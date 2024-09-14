package com.soro.esop.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.soro.esop.model.UserDto;

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
public class UserValidator implements Validator {

    @Override
    public boolean supports(@SuppressWarnings("null") Class<?> clazz) {
        log.debug("clazz: {}", clazz);
        return clazz.isAssignableFrom(UserDto.class);
    }

    @Override
    public void validate(@SuppressWarnings("null") Object target, 
                         @SuppressWarnings("null") Errors errors) {
        log.debug("target: {}", target);
        log.debug("errors: {}", errors);

        UserDto user = (UserDto) target;
        log.debug("user: {}", user);

        if(user.getUsername() == null || user.getUsername().isEmpty()) {
            errors.rejectValue("username", "username.empty", new Object[] {}, "아이디를 입력해주세요.");
        }

        if(user.getUsername().length() < 2) {
            errors.rejectValue("username", "username.min.length", new Object[] {2}, "아이디는 2자 이상 입력해주세요.");
        }
    }

    
}
