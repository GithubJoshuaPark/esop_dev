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
    public String entityList(Model model)
    {
        log.debug("dx/entityList");
        model.addAttribute("welcomeMessage", "Welcome to the Dx List");
        return "dx/entityList"; // dx/entityList.html
    }

    @GetMapping("/stockBuyList")
    public String stockBuyList(Model model)
    {
        log.debug("dx/stockBuyList");
        model.addAttribute("welcomeMessage", "Welcome to the Dx StockBuyList");
        return "dx/stockBuyList";
    }

    @GetMapping("/userList")
    public String userList(Model model)
    {
        log.debug("dx/userList");
        model.addAttribute("welcomeMessage", "Welcome to the Dx List");
        return "dx/userList";
    }

    @GetMapping("/company")
    public String openCompanyPage(Model model)
    {
        log.debug("dx/company");
        model.addAttribute("welcomeMessage", "Welcome to the Dx Company");
        return "dx/company";
    }

    @GetMapping("/bizUnit")
    public String openBizUnitPage(Model model)
    {
        log.debug("dx/bizUnit");
        model.addAttribute("welcomeMessage", "Welcome to the Dx BizUnit");
        return "dx/bizUnit";
    }

    @GetMapping("/dept")
    public String openDeptPage(Model model)
    {
        log.debug("dx/dept");
        model.addAttribute("welcomeMessage", "Welcome to the Dx Dept");
        return "dx/dept";
    }

    @GetMapping("/team")
    public String openTeamPage(Model model)
    {
        log.debug("dx/team");
        model.addAttribute("welcomeMessage", "Welcome to the Dx Team");
        return "dx/team";
    }

    @GetMapping("/acct")
    public String openAcctPage(Model model)
    {
        log.debug("dx/acct");
        model.addAttribute("welcomeMessage", "Welcome to the Dx Acct");
        return "dx/acct";
    }

    @GetMapping("/member")
    public String openMemberPage(Model model)
    {
        log.debug("dx/member");
        model.addAttribute("welcomeMessage", "Welcome to the Dx Member");
        return "dx/member";
    }


}
