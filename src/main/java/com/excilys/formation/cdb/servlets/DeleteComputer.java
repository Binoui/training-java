package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.pagination.ComputerListPage;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet(name = "DeleteComputer", urlPatterns = "/DeleteComputer")
public class DeleteComputer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final ComputerDTOMapper computerMapper = ComputerDTOMapper.INSTANCE;
    private static final ComputerService computerService = ComputerService.INSTANCE;
    private static final Logger Logger = LoggerFactory.getLogger(DeleteComputer.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/Dashboard").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String selectedIds = request.getParameter("selection").trim();
        if (selectedIds.isEmpty()) {
            getServletContext().getRequestDispatcher("/Dashboard").forward(request, response);
            return;
        }
        
        Arrays.asList(selectedIds.split(","));
        
        
        doGet(request, response);
    }

}
