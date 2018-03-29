package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.pagination.ComputerListPage;
import com.excilys.formation.cdb.pagination.ComputerListPageSearch;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet(name = "Dashboard", urlPatterns = "/Dashboard")
public class Dashboard extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final ComputerDTOMapper computerMapper = ComputerDTOMapper.INSTANCE;
    private static final Logger Logger = LoggerFactory.getLogger(Dashboard.class);

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

        ComputerListPage page;
        String searchWord = request.getParameter("search");

        String sortBy = request.getParameter("sortBy");
        String ascendingString = request.getParameter("ascending");

        try {
            if (searchWord != null && !searchWord.trim().isEmpty()) {
                page = new ComputerListPageSearch(searchWord.trim());
            } else {
                page = new ComputerListPage();
            }

            if (sortBy != null && ascendingString != null) {
                SortableComputerColumn column;
                boolean ascending;

                try {
                    column = SortableComputerColumn.valueOf(sortBy.trim().toUpperCase());

                    if (ascendingString.equalsIgnoreCase("true") || ascendingString.equalsIgnoreCase("false")) {

                        ascending = Boolean.valueOf(ascendingString);
                        page.setColumn(column);
                        page.setAscendingSort(ascending);

                    } else {
                        Logger.debug("Couldn't parse given ascending value into a boolean");
                    }

                } catch (IllegalArgumentException e) {
                    Logger.debug("wrong computercolumn given, cannot parse into enum {}", e);
                }
            }

            handleRequest(request, page);
        } catch (ServiceException e) {
            Logger.error("Error creating page{}", e);
        }

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

    private int getIntParam(String param, int defaultValue) {
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void handleRequest(HttpServletRequest request, ComputerListPage page) {
        String pageNumber = request.getParameter("pageNumber");

        List<ComputerDTO> listComputers = new LinkedList<>();
        try {
            if (pageNumber != null) {
                page.goToPage(getIntParam(pageNumber, 0));
            }

            String itemsPerPage = request.getParameter("itemsPerPage");
            if (itemsPerPage != null) {
                page.setPageSize(getIntParam(itemsPerPage, 10));
            }

            page.getPage().forEach(computer -> listComputers.add(computerMapper.createComputerDTO(computer)));

        } catch (ServiceException e) {
            Logger.error("Error creating page{}", e);
            return;
        }

        request.setAttribute("computers", listComputers);
        request.setAttribute("pageNumber", page.getPageNumber());
        try {
            request.setAttribute("pageCount", page.getListComputersPageCount(page.getPageSize()));
            request.setAttribute("computerCount", page.getComputerCount());
        } catch (ServiceException e) {
            Logger.error("Error accessing service {}", e);
            return;
        }
    }

}
