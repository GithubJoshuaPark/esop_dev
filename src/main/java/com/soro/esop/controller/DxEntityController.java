package com.soro.esop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-17 16:52:28
 * @modify date : 2024-08-17 16:52:28
 * @desc : [Routes.java] 
 */

@Slf4j
@Controller
@RequestMapping("/dx")
@RequiredArgsConstructor
public class DxEntityController {

    @GetMapping("/entityList")
    public String boardList(Model model)
    {
        log.debug("dx/entityList");
        model.addAttribute("welcomeMessage", "Welcome to the Dx List");
        return "dx/entityList"; // dx/entityList.html
    }

}
