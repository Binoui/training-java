package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.pagination.ComputerListPage;
import com.excilys.formation.cdb.pagination.ComputerListPageSearch;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

@Controller
public class DashboardController {

    private static final Logger Logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ComputerService computerService;

    public DashboardController() {
        super();
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    protected ModelAndView doGet(@RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "ascending", required = false) boolean ascending,
            @RequestParam(value = "searchWord", required = false) String searchWord, ModelAndView modelAndView) {

        ComputerListPage page;
        modelAndView.setViewName("dashboard");

        try {
            if (!StringUtils.isBlank(searchWord)) {
                page = new ComputerListPageSearch(searchWord, computerService);
            } else {
                page = new ComputerListPage(computerService);
            }

            putOrderByOnPage(page, sortBy, ascending);
            fillModelAndViewWithPageArgs(modelAndView, page);
        } catch (ServiceException e) {
            Logger.error("Error creating page{}", e);
        }

        return modelAndView;
    }

    private int getIntParam(String param, int defaultValue) {
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void fillModelAndViewWithPageArgs(ModelAndView modelAndView, ComputerListPage page) {

        Map<String, Object> argsMap = modelAndView.getModelMap();
        List<ComputerDTO> listComputers = new LinkedList<>();

        try {
            String pageNumber = (String) argsMap.get("pageNumber");
            if (pageNumber != null) {
                page.goToPage(getIntParam(pageNumber, 0));
            }

            String itemsPerPage = (String) argsMap.get("itemsPerPage");
            if (itemsPerPage != null) {
                page.setPageSize(getIntParam(itemsPerPage, 10));
            }

            page.getPage().forEach(computer -> listComputers.add(ComputerDTOMapper.createComputerDTO(computer)));
        } catch (ServiceException e) {
            Logger.error("Error creating page{}", e);
            return;
        }

        argsMap.put("computers", listComputers);
        argsMap.put("pageNumber", page.getPageNumber());
        try {
            argsMap.put("pageCount", page.getListComputersPageCount(page.getPageSize()));
            argsMap.put("computerCount", page.getComputerCount());
        } catch (ServiceException e) {
            Logger.error("Error accessing service {}", e);
            return;
        }
    }

    private void putOrderByOnPage(ComputerListPage page, String sortBy, boolean ascending) {
        if (!StringUtils.isBlank(sortBy)) {
            SortableComputerColumn column;
            try {
                column = SortableComputerColumn.valueOf(sortBy.toUpperCase());
                page.setColumn(column);
                page.setAscendingSort(ascending);

            } catch (IllegalArgumentException e) {
                Logger.debug("wrong computercolumn given, cannot parse into enum {}", e);
            }
        }
    }

}
