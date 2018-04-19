package com.excilys.formation.cdb.controllers.web;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.pagination.ComputerListPage;
import com.excilys.formation.cdb.pagination.ComputerListPageSearch;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;
import com.excilys.formation.cdb.validators.IncorrectValidationException;
import com.excilys.formation.cdb.validators.InvalidDatesException;

@Controller
@RequestMapping(value = "/computer")
public class ComputerController {

    private static final Logger Logger = LoggerFactory.getLogger(ComputerController.class);

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    public ComputerController() {
        super();
    }

    @GetMapping(value = "/dashboard")
    private ModelAndView dashboardGet(@RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "ascending", required = false) boolean ascending,
            @RequestParam(value = "searchWord", required = false) String searchWord,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "itemsPerPage", required = false, defaultValue = "10") int itemsPerPage,
            ModelAndView modelAndView) {

        ComputerListPage page;
        modelAndView.setViewName("dashboard");

        try {
            if (!StringUtils.isBlank(searchWord)) {
                page = new ComputerListPageSearch(searchWord, computerService);
            } else {
                page = new ComputerListPage(computerService);
            }

            page.goToPage(pageNumber);
            page.setPageSize(itemsPerPage);

            putOrderByOnPage(page, sortBy, ascending);
            fillModelAndViewWithPageArgs(modelAndView, page);
        } catch (ServiceException e) {
            Logger.error("Error creating page{}", e);
        }

        return modelAndView;
    }

    @GetMapping(value = "/add")
    private ModelAndView addComputerGet(ModelAndView modelAndView) {

        List<CompanyDTO> listCompanies = new LinkedList<>();

        try {
            companyService.getListCompanies()
                    .forEach(company -> listCompanies.add(CompanyDTOMapper.createCompanyDTO(company)));
        } catch (ServiceException e) {
            Logger.debug("Cannot list companies {}", e);
        }

        modelAndView.addObject("computerDTO", new ComputerDTO());
        modelAndView.addObject("companies", listCompanies);
        modelAndView.setViewName("addComputer");

        return modelAndView;
    }

    @PostMapping(value = "/add")
    private ModelAndView addComputerPost(ComputerDTO newComputerDto, ModelAndView modelAndView, RedirectAttributes attributes) {

        Computer newComputer;
        modelAndView.setViewName("redirect:/computer/add");

        try {
            newComputer = ComputerDTOMapper.createComputerFromDto(newComputerDto);
            computerService.createComputer(newComputer);
            attributes.addFlashAttribute("success", "Computer added !");
        } catch (ServiceException | IncorrectValidationException e) {
            Logger.debug("Cannot create computer {}", e);
            attributes.addFlashAttribute("error", e.getMessage());
        }

        return modelAndView;
    }

    @PostMapping(value = "/delete")
    private ModelAndView deleteComputerPost(@RequestParam(value = "selection") List<Long> idsToDelete,
            ModelAndView modelAndView, RedirectAttributes attributes) {

        modelAndView.setViewName("redirect:/computer/dashboard");

        try {
            computerService.deleteComputers(idsToDelete);
            attributes.addFlashAttribute("success", "Computers deleted !");
        } catch (ServiceException e) {
            attributes.addFlashAttribute("error", "Cannot delete computers, wrong selection");
        }

        return modelAndView;
    }

    @GetMapping(value = "/edit")
    private ModelAndView editComputerGet(@RequestParam(value = "computerId") Long computerId,
            ModelAndView modelAndView) {

        List<CompanyDTO> listCompanies = new LinkedList<>();
        Optional<Computer> optionalComputer;

        try {
            optionalComputer = computerService.getComputer(new ComputerBuilder().withId(computerId).build());

            if (!optionalComputer.isPresent()) {
                modelAndView.addObject("error", "Cannot find computer");
                modelAndView.setViewName("dashboard");
                return modelAndView;
            }

            modelAndView.addObject("computer", ComputerDTOMapper.createComputerDTO(optionalComputer.get()));

            companyService.getListCompanies()
                    .forEach(company -> listCompanies.add(CompanyDTOMapper.createCompanyDTO(company)));

        } catch (ServiceException e) {
            Logger.error("error accessing service {}", e);
            e.printStackTrace();
        }

        modelAndView.addObject("companies", listCompanies);
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    private ModelAndView deleteComputerPost(@RequestParam(value = "computerName") String name,
            @RequestParam(value = "computerId") Long computerId,
            @RequestParam(value = "introduced", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate introduced,
            @RequestParam(value = "discontinued", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate discontinued,
            @RequestParam(value = "companyId") Long companyId, ModelAndView modelAndView) {

        modelAndView.setViewName("dashboard");

        ComputerBuilder computerBuilder = new ComputerBuilder().withId(computerId).withName(name)
                .withIntroduced(introduced).withDiscontinued(discontinued);
        if (companyId != null) {
            computerBuilder.withCompany(new CompanyBuilder().withId(companyId).build());
        }

        Computer computer = computerBuilder.build();

        try {
            computerService.updateComputer(computer);
        } catch (ServiceException | IncorrectValidationException e) {
            Logger.debug("Cannot edit computer {}", e);
            modelAndView.addObject("error", e.getMessage());
        }

        return modelAndView;
    }

    private void fillModelAndViewWithPageArgs(ModelAndView modelAndView, ComputerListPage page) {

        Map<String, Object> argsMap = modelAndView.getModelMap();
        List<ComputerDTO> listComputers = new LinkedList<>();

        try {
            page.getPage().forEach(computer -> listComputers.add(ComputerDTOMapper.createComputerDTO(computer)));
        } catch (ServiceException e) {
            Logger.error("Error creating page{}", e);
            return;
        }

        argsMap.put("computers", listComputers);
        argsMap.put("pageNumber", page.getPageNumber());
        argsMap.put("itemsPerPage", page.getPageSize());
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
