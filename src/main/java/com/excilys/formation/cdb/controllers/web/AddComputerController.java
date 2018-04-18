package com.excilys.formation.cdb.controllers.web;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

@Controller
public class AddComputerController {
    private static final Logger Logger = LoggerFactory.getLogger(AddComputerController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    public AddComputerController() {
        super();
    }

    private Computer createComputerFromParameters(String name, LocalDate introduced, LocalDate discontinued,
            Long companyId) {
        ComputerBuilder computerBuilder = new ComputerBuilder().withName(name).withIntroduced(introduced)
                .withDiscontinued(discontinued);

        if (companyId != 0) {
            computerBuilder.withCompany(new CompanyBuilder().withId(companyId).build());
        }

        return computerBuilder.build();
    }

    @RequestMapping(value = "/addComputer", method = RequestMethod.GET)
    private ModelAndView doGet(ModelAndView modelAndView) {

        List<CompanyDTO> listCompanies = new LinkedList<>();

        try {
            companyService.getListCompanies()
                    .forEach(company -> listCompanies.add(CompanyDTOMapper.createCompanyDTO(company)));
        } catch (ServiceException e) {
            Logger.debug("Cannot list companies {}", e);
        }

        modelAndView.addObject("companies", listCompanies);
        
        return modelAndView;
    }

    @RequestMapping(value = "/addComputer", method = RequestMethod.POST)
    private ModelAndView doPost(@RequestParam(value = "computerName") String name,
            @RequestParam(value = "introduced", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate introduced,
            @RequestParam(value = "discontinued", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate discontinued,
            @RequestParam(value = "companyId") Long companyId, ModelAndView modelAndView) {

        Computer newComputer = createComputerFromParameters(name, introduced, discontinued, companyId);

        try {
            computerService.createComputer(newComputer);
        } catch (ServiceException | IncorrectValidationException e) {
            Logger.debug("Cannot create computer {}", e);
            modelAndView.addObject("error", e.getMessage());
        }

        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
}
