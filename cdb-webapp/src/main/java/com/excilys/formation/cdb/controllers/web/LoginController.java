package com.excilys.formation.cdb.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    private ModelAndView addComputerGet(ModelAndView modelAndView) {

        modelAndView.setViewName("login");
        return modelAndView;
    }
}
