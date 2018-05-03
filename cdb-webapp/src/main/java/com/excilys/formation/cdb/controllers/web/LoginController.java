package com.excilys.formation.cdb.controllers.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    private ModelAndView loginGet(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/logout")
    private ModelAndView logoutGet(ModelAndView modelAndView) {
        SecurityContextHolder.getContext().setAuthentication(null);
        modelAndView.addObject("logout", "Logout complete !");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/403")
    private ModelAndView handle403Get(ModelAndView modelAndView) {
        modelAndView.setViewName("403");
        return modelAndView;
    }

    @PostMapping(value = "/403")
    private ModelAndView handle403Post(ModelAndView modelAndView) {
        modelAndView.setViewName("403");
        return modelAndView;
    }

}
