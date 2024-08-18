package com.soro.esop.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.soro.esop.dto.UserDto;

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

        // if(user.getPassword() == null || user.getPassword().isEmpty()) {
        //     errors.rejectValue("password", "password.empty", new Object[] {}, "비밀번호를 입력해주세요.");
        // }

        // if(user.getPassword().length() < 8) {
        //     errors.rejectValue("password", "password.min.length", new Object[] {8}, "비밀번호는 8자 이상 입력해주세요.");
        // }

        // if(user.getPassword().length() > 20) {
        //     errors.rejectValue("password", "password.max.length", new Object[] {20}, "비밀번호는 20자 이하로 입력해주세요.");
        // }

        // if(user.getPassword().equals(user.getUsername())) {
        //     errors.rejectValue("password", "password.equal.username", new Object[] {}, "아이디와 비밀번호는 같을 수 없습니다.");
        // }
    }

    
}
