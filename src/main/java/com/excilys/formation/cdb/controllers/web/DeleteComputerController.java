package com.excilys.formation.cdb.controllers.web;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

@Controller
public class DeleteComputerController {

    private static final Logger Logger = LoggerFactory.getLogger(DeleteComputerController.class);
    @Autowired
    private ComputerService computerService;

    public DeleteComputerController() {
        super();
    }

    @RequestMapping(value = "/deleteComputer", method = RequestMethod.GET)
    private void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @RequestMapping(value = "/deleteComputer", method = RequestMethod.POST)
    private ModelAndView doPost(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {

        String selectedIds = request.getParameter("selection").trim();
        if (selectedIds.isEmpty()) {
            modelAndView.setViewName("dashboard");
            return modelAndView;
        }

        List<Long> idsToDelete = new LinkedList<>();

        try {
            Arrays.asList(selectedIds.split(",")).forEach(id -> idsToDelete.add(Long.parseLong(id)));
        } catch (NumberFormatException e) {
            redirectToDashboardWithError(request, response, "Cannot delete computers, wrong selection");
            return modelAndView;
        }

        try {
            computerService.deleteComputers(idsToDelete);
        } catch (ServiceException e) {
            redirectToDashboardWithError(request, response, "cannot delete computers");
            return modelAndView;
        }

        doGet(request, response);
        return modelAndView;
    }

    private void redirectToDashboardWithError(HttpServletRequest request, HttpServletResponse response, String error)
             {
        Logger.error(error);
        request.setAttribute("error", error);
    }

}
