package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.pagination.ComputerListPage;
import com.excilys.formation.cdb.pagination.ComputerListPageSearch;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

@Controller
@RequestMapping("/dashboard")
public class Dashboard {

    private static final Logger Logger = LoggerFactory.getLogger(Dashboard.class);

    @Autowired
    private ComputerService computerService;

    public Dashboard() {
        super();
    }

    @RequestMapping(value= "/dashboard", method=RequestMethod.GET)
    protected String doGet(Model model)
            throws ServletException, IOException {

        ComputerListPage page;
        String searchWord = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String ascendingString = request.getParameter("ascending");

        try {
            if (!StringUtils.isBlank(searchWord)) {
                page = new ComputerListPageSearch(searchWord, computerService);
            } else {
                page = new ComputerListPage(computerService);
            }

            putOrderByOnPage(page, sortBy, ascendingString);
            handleRequest(request, page);
        } catch (ServiceException e) {
            Logger.error("Error creating page{}", e);
        }

        return "dashboard";
    }

    @RequestMapping(value= "/dashboard", method=RequestMethod.POST)
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

            page.getPage().forEach(computer -> listComputers.add(ComputerDTOMapper.createComputerDTO(computer)));

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

    private void putOrderByOnPage(ComputerListPage page, String sortBy, String ascendingString) {
        if (!StringUtils.isBlank(sortBy) && !StringUtils.isBlank(ascendingString)) {
            SortableComputerColumn column;
            boolean ascending;

            try {
                column = SortableComputerColumn.valueOf(sortBy.toUpperCase());

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
    }

}
