package com.excilys.formation.cdb.controllers.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    private ModelAndView loginGet(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/logout")
    private ModelAndView logoutGet(ModelAndView modelAndView, RedirectAttributes attributes) {
        SecurityContextHolder.getContext().setAuthentication(null);
        attributes.addFlashAttribute("logout");
        attributes.addFlashAttribute("error", "error!!!!");
        modelAndView.setViewName("login");
        return modelAndView;
    }

}
