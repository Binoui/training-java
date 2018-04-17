package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
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
public class AddComputer {
    private static final Logger Logger = LoggerFactory.getLogger(AddComputer.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    public AddComputer() {
        super();
    }

    private Computer createComputerFromParameters(String name, LocalDate introduced, LocalDate discontinued,
            Long companyId) {
        ComputerBuilder computerBuilder = new ComputerBuilder().withName(name).withIntroduced(introduced)
                .withDiscontinued(discontinued).withCompany(new CompanyBuilder().withId(companyId).build());
        return computerBuilder.build();
    }

    @RequestMapping(value = "/addComputer", method = RequestMethod.GET)
    protected ModelAndView doGet(ModelAndView modelAndView) {

        List<CompanyDTO> listCompanies = new LinkedList<>();
        try {
            companyService.getListCompanies()
                    .forEach(company -> listCompanies.add(CompanyDTOMapper.createCompanyDTO(company)));
        } catch (ServiceException e) {
            Logger.debug("Cannot list companies {}", e);
        }
        modelAndView.addObject(listCompanies);
        modelAndView.setViewName("addComputer");
        return modelAndView;
    }

    @RequestMapping(value = "/addComputer", method = RequestMethod.POST)
    protected void doPost(@RequestParam(value = "computerName") String name,
            @RequestParam(value = "introduced") LocalDate introduced,
            @RequestParam(value = "discontinued") LocalDate discontinued,
            @RequestParam(value = "companyId") Long companyId, ModelAndView modelAndView) {

        Computer newComputer = null;

        try {
            newComputer = createComputerFromParameters(name, introduced, discontinued, companyId);
        } catch (Exception e) {
            Logger.error("couldn't create computer from parameters : ", e);
        }

        if (newComputer == null) {
            return;
        }

        try {
            computerService.createComputer(newComputer);
        } catch (ServiceException | IncorrectValidationException e) {
            Logger.debug("Cannot create computer {}", e);
            modelAndView.addObject("error", e.getMessage());
        }

    }
}
