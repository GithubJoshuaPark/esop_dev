package com.soro.esop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-17 16:52:28
 * @modify date : 2024-08-17 16:52:28
 * @desc : [Routes.java] 
 */

@Slf4j
@Controller
@RequestMapping("/path")
@RequiredArgsConstructor
public class PathListController {

    @GetMapping("/list")
    public String boardList(Model model)
    {
        log.debug("path/list");
        model.addAttribute("welcomeMessage", "Welcome to the Dx Path List");
        return "path/list"; // path/list.html
    }

}
