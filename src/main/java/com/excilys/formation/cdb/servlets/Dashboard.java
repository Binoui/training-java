package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.pagination.ComputerListPage;
import com.excilys.formation.cdb.services.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet(name = "Dashboard", urlPatterns = "/Dashboard")
public class Dashboard extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static ComputerDTOMapper computerMapper = ComputerDTOMapper.INSTANCE;
    private static ComputerService computerService = ComputerService.INSTANCE;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ComputerListPage page = new ComputerListPage();
        handleRequest(request, page);
        getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void handleRequest(HttpServletRequest request, ComputerListPage page) {
        String pageNumber = request.getParameter("pageNumber");
        try {
            if (pageNumber != null) {
                page.goToPage(Integer.parseInt(pageNumber));
            }

            String itemsPerPage = request.getParameter("itemsPerPage");
            if (itemsPerPage != null) {
                page.setPageSize(Integer.parseInt(itemsPerPage));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        List<ComputerDTO> listComputers = new LinkedList<>();
        page.getPage().forEach(computer -> listComputers.add(computerMapper.createComputerDTO(computer)));
        request.setAttribute("computers", listComputers);
        request.setAttribute("pageNumber", page.getPageNumber());
        request.setAttribute("computerCount", computerService.getComputerCount());
        request.setAttribute("pageCount", computerService.getListComputersPageCount(page.getPageSize()));
    }

}
