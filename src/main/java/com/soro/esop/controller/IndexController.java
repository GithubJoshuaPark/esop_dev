package com.soro.esop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-18 01:02:20
 * @modify date : 2024-08-18 01:02:20
 * @desc [index view controller]
 */

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping
    public String index(Model model) {
        return "index";
    }
}
