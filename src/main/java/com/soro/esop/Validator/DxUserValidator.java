package com.soro.esop.Validator;

import com.soro.esop.entity.DxUser;
import com.soro.esop.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-17 16:52:48
 * @modify date : 2024-08-17 16:52:48
 * @desc [description]
 */

@Slf4j
@Component
public class DxUserValidator implements Validator {

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

        DxUser user = (DxUser) target;
        log.debug("user: {}", user);

        if(user.getName() == null || user.getName().isEmpty()) {
            errors.rejectValue("username", "username.empty", new Object[] {}, "아이디를 입력해주세요.");
        }

        if(user.getName().length() < 2) {
            errors.rejectValue("username", "username.min.length", new Object[] {2}, "아이디는 2자 이상 입력해주세요.");
        }

        if(user.getAddress() == null || user.getAddress().isEmpty()) {
            errors.rejectValue("address", "address.empty", new Object[] {}, "주소를 입력해주세요.");
        }

        if(user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            errors.rejectValue("phoneNumber", "phoneNumber.empty", new Object[] {}, "전화번호를 입력해주세요.");
        }

        if(user.getSsn() == null || user.getSsn().isEmpty()) {
            errors.rejectValue("ssn", "ssn.empty", new Object[] {}, "주민등록번호를 입력해주세요.");
        }

        if(user.getSsn().length() != 13) {
            errors.rejectValue("ssn", "ssn.min.length", new Object[] {13}, "주민등록번호는 13자리 입니다.");
        }

        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.rejectValue("email", "email.empty", new Object[] {}, "이메일을 입력해주세요.");
        }

        // email validation
        if(!user.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            errors.rejectValue("email", "email.invalid", new Object[] {}, "이메일 형식이 올바르지 않습니다.");
        }

    }

    
}
