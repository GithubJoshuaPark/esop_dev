package com.soro.esop.controller;

import java.time.LocalDate;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soro.esop.entiry.User;
import com.soro.esop.service.UserService;

import jakarta.servlet.http.HttpSession;


@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    
    private final UserService userService;

    @GetMapping("/list")
    public String index(Model model) {
        return "board/list";
    }

}
