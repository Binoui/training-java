package com.excilys.formation.cdb.controllers.web;

import java.net.BindException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.pagination.ComputerListPage;
import com.excilys.formation.cdb.pagination.ComputerListPageSearch;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

@Controller
@RequestMapping(value = "/computer")
public class ComputerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

    private ComputerService computerService;

    private CompanyService companyService;

    public ComputerController(CompanyService companyService, ComputerService computerService) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
    }

    @GetMapping(value = "/add")
    private ModelAndView addComputerGet(ModelAndView modelAndView) {

        List<CompanyDTO> listCompanies = new LinkedList<>();

        try {
            companyService.getListCompanies()
                    .forEach(company -> listCompanies.add(CompanyDTOMapper.createCompanyDTO(company)));
        } catch (ServiceException e) {
            LOGGER.debug("Cannot list companies {}", e);
        }

        modelAndView.addObject("computerDTO", new ComputerDTO());
        modelAndView.addObject("companies", listCompanies);
        modelAndView.setViewName("addComputer");

        return modelAndView;
    }

    @PostMapping(value = "/add")
    @ExceptionHandler(BindException.class)
    private ModelAndView addComputerPost(ComputerDTO newComputerDto, ModelAndView modelAndView,
            RedirectAttributes attributes) {

        modelAndView.setViewName("redirect:/computer/add");
//
//        if (errors.hasErrors()) {
//            Logger.debug("error in computer DTO");
//            attributes.addFlashAttribute("error", "test");
//            return new ModelAndView("redirect:/computer/add");
//        }
//        

        try {
            Computer newComputer = ComputerDTOMapper.createComputerFromDto(newComputerDto);
            computerService.createComputer(newComputer);
            attributes.addFlashAttribute("success", "Computer added !");
        } catch (ServiceException | IncorrectValidationException e) {
            LOGGER.debug("Cannot create computer {}", e);
            attributes.addFlashAttribute("error", e.getMessage());
        }

        return modelAndView;
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
            LOGGER.error("Error creating page{}", e);
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
    private ModelAndView editComputerGet(@RequestParam(value = "computerId") Long computerId, ModelAndView modelAndView,
            RedirectAttributes attributes) {

        List<CompanyDTO> listCompanies = new LinkedList<>();
        Optional<Computer> optionalComputer;

        try {
            optionalComputer = computerService.getComputer(new ComputerBuilder().withId(computerId).build());

            if (!optionalComputer.isPresent()) {
                attributes.addFlashAttribute("error", "Cannot delete computers, wrong selection");
                modelAndView.setViewName("redirect:/computer/dashboard");
                return modelAndView;
            }

            modelAndView.addObject("computerDTO", ComputerDTOMapper.createComputerDTO(optionalComputer.get()));

            companyService.getListCompanies()
                    .forEach(company -> listCompanies.add(CompanyDTOMapper.createCompanyDTO(company)));

        } catch (ServiceException e) {
            LOGGER.error("error accessing service {}", e);
            e.printStackTrace();
        }

        modelAndView.setViewName("editComputer");
        modelAndView.addObject("companies", listCompanies);
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    private ModelAndView editComputerPost(@Valid ComputerDTO computerDto, ModelAndView modelAndView,
            RedirectAttributes attributes) {

        try {
            computerService.updateComputer(ComputerDTOMapper.createComputerFromDto(computerDto));
            attributes.addFlashAttribute("computerId", computerDto.getId());
            attributes.addFlashAttribute("success", "Computer updated !");
        } catch (ServiceException | IncorrectValidationException e) {
            LOGGER.debug("Cannot edit computer {}", e);
            attributes.addFlashAttribute("error", e.getMessage());
        }

        modelAndView.setViewName("redirect:/computer/edit?computerId=" + computerDto.getId());
        return modelAndView;
    }

    private void fillModelAndViewWithPageArgs(ModelAndView modelAndView, ComputerListPage page) {

        Map<String, Object> argsMap = modelAndView.getModelMap();
        List<ComputerDTO> listComputers = new LinkedList<>();

        try {
            page.getPage().forEach(computer -> listComputers.add(ComputerDTOMapper.createComputerDTO(computer)));
        } catch (ServiceException e) {
            LOGGER.error("Error creaing page{}", e);
            return;
        }

        argsMap.put("computers", listComputers);
        argsMap.put("pageNumber", page.getPageNumber());
        argsMap.put("itemsPerPage", page.getPageSize());
        try {
            argsMap.put("pageCount", page.getListComputersPageCount(page.getPageSize()));
            argsMap.put("computerCount", page.getComputerCount());
        } catch (ServiceException e) {
            LOGGER.error("Error accessing service {}", e);
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
                LOGGER.debug("wrong computercolumn given, cannot parse into enum {}", e);
            }
        }
    }

}
