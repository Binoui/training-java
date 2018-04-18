package com.excilys.formation.cdb.controllers.web;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

@Controller
public class EditComputerController {
    private static final Logger Logger = LoggerFactory.getLogger(EditComputerController.class);
    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerService computerService;

    public EditComputerController() {
        super();
    }

    @RequestMapping(value = "/editComputer", method = RequestMethod.GET)
    private ModelAndView doGet(@RequestParam(value = "computerId") Long computerId, ModelAndView modelAndView) {

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

    @RequestMapping(value = "/editComputer", method = RequestMethod.POST)
    private ModelAndView doPost(@RequestParam(value = "computerName") String name, @RequestParam(value = "computerId") Long computerId,
            @RequestParam(value = "introduced", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate introduced,
            @RequestParam(value = "discontinued", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate discontinued,
            @RequestParam(value = "companyId") Long companyId, ModelAndView modelAndView) {

        modelAndView.setViewName("dashboard");
        
        ComputerBuilder computerBuilder = new ComputerBuilder().withId(computerId).withName(name).withIntroduced(introduced).withDiscontinued(discontinued);
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
}
