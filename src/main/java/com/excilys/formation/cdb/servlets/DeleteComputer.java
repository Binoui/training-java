package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

@WebServlet(name = "DeleteComputer", urlPatterns = "/DeleteComputer")
public class DeleteComputer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger Logger = LoggerFactory.getLogger(DeleteComputer.class);
    @Autowired
    private ComputerService computerService;

    public void init(ServletConfig config) {
        try {
            super.init(config);
        } catch (ServletException e) {
            Logger.error("error while trying to initialize servlet dashboard");
        }
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    public DeleteComputer() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/Dashboard").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String selectedIds = request.getParameter("selection").trim();
        if (selectedIds.isEmpty()) {
            getServletContext().getRequestDispatcher("/Dashboard").forward(request, response);
            return;
        }

        List<Long> idsToDelete = new LinkedList<>();

        try {
            Arrays.asList(selectedIds.split(",")).forEach(id -> idsToDelete.add(Long.parseLong(id)));
        } catch (NumberFormatException e) {
            redirectToDashboardWithError(request, response, "Cannot delete computers, wrong selection");
            return;
        }

        try {
            computerService.deleteComputers(idsToDelete);
        } catch (ServiceException e) {
            redirectToDashboardWithError(request, response, "cannot delete computers");
            return;
        }

        doGet(request, response);
    }

    private void redirectToDashboardWithError(HttpServletRequest request, HttpServletResponse response, String error)
            throws ServletException, IOException {
        Logger.error(error);
        request.setAttribute("error", error);
        getServletContext().getRequestDispatcher("/Dashboard").forward(request, response);
    }

}
