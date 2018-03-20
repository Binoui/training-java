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
        
        String pageNumber = request.getParameter("pageNumber");
        if (pageNumber != null) {
            page.goToPage(Integer.parseInt(pageNumber));
        }
        
        ComputerDTOMapper mapper = ComputerDTOMapper.INSTANCE;
        List<ComputerDTO> listComputers = new LinkedList<>();
        page.getPage().forEach(computer -> listComputers.add(mapper.createComputerDTO(computer)));

        request.setAttribute("computers", listComputers);

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

}
