package com.soro.esop.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soro.esop.Validator.UserValidator;
import com.soro.esop.entity.Role;
import com.soro.esop.entity.User;
import com.soro.esop.entity.UserRole;
import com.soro.esop.mapper.UserMapper;
import com.soro.esop.model.UserDto;
import com.soro.esop.service.RoleService;
import com.soro.esop.service.UserRoleService;
import com.soro.esop.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-18 01:02:20
 * @modify date : 2024-08-18 01:02:20
 * @desc [account view controller]
 */

@Slf4j
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountViewController {
    
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    private final UserValidator userValidator;
    private final BCryptPasswordEncoder passwordEncoder; // 패스워드 인코더

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new UserDto());
        return "account/login";   // go to resources/templates/account/login.html
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new UserDto());
        return "account/register"; // go to resources/templates/account/register.html
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto userDto,
                            BindingResult result,
                            Model model) 
    {
        log.debug("userDto: {}", userDto);
        userValidator.validate(userDto, result);
        if (result.hasErrors()) {
            log.debug("errors: {}", result.getAllErrors());            

            //model.addAttribute("user", userDto);
            return "account/register";
        }

        // 패스워드 암호화
        String encrptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encrptedPassword);

        // 사용자 정보 저장
        User user = UserMapper.toEntity(userDto);
        userService.save(user);

        Role role = roleService.findById(1L);        
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        
        // 사용자 권한 저장
        userRoleService.save(userRole);
        
        return "redirect:/account/login";
    }
}
