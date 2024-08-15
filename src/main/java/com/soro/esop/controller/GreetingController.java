package com.soro.esop.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soro.esop.dto.UserDto;
import com.soro.esop.entiry.User;
import com.soro.esop.mapper.UserMapper;
import com.soro.esop.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GreetingController {

    private final UserService userService;

    @GetMapping("/greeting")
    public String getMethodName(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
            HttpSession session,
            Model model) {
        session.setAttribute("welcomeMsg", "Hello " + name);

        LocalDate today = LocalDate.now();
        log.debug("param: {}, today: {}", name, today);

        List<User> userList = userService.findAll();
        log.debug("users: {}", userList);

        // convert users to List<UserDto> using UserMapper
        List<UserDto> users = UserMapper.toDto(userList);

        model.addAttribute("name", name);
        model.addAttribute("today", today);
        model.addAttribute("users", users);

        return "greeting"; // go to greeting.html
    }

    @Component("helloBean")
    static class HelloBean {
        public String sayHello(String data) {
            return "Hello, World " + data;
        }
    }

}
