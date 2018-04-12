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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet(name = "AddComputer", urlPatterns = "/AddComputer")
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger Logger = LoggerFactory.getLogger(AddComputer.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    public void init(ServletConfig config) {
        try {
            super.init(config);
        } catch (ServletException e) {
            Logger.error("error while trying to initialize servlet addcomputer");
        }
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    public AddComputer() {
        super();
    }

    private Computer createComputerFromParameters(HttpServletRequest request, HttpServletResponse response, String name,
            String introducedString, String discontinuedString, String companyIdString)
            throws ServletException, IOException {

        ComputerBuilder computerBuilder = new ComputerBuilder().withName(name);

        try {
            if ((introducedString != null) && !introducedString.isEmpty()) {
                computerBuilder.withIntroduced(LocalDate.parse(introducedString));
            }

            if ((discontinuedString != null) && !discontinuedString.isEmpty()) {
                computerBuilder.withDiscontinued(LocalDate.parse(discontinuedString));
            }
        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Wrong date format");
            Logger.error("Cannot create computer, wrong date format {}", e);
            doGet(request, response);
        }

        if ((companyIdString != null) && !companyIdString.isEmpty()) {
            Long companyId = Long.parseLong(companyIdString);
            if (companyId != 0) {
                computerBuilder.withCompany(new CompanyBuilder().withId(companyId).build());
            }
        }

        return computerBuilder.build();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CompanyDTO> listCompanies = new LinkedList<>();

        try {
            companyService.getListCompanies()
                    .forEach(company -> listCompanies.add(CompanyDTOMapper.createCompanyDTO(company)));
        } catch (ServiceException e) {
            Logger.debug("Cannot list companies {}", e);
        }

        request.setAttribute("companies", listCompanies);

        getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("computerName").trim();
        String introducedString = request.getParameter("introduced").trim();
        String discontinuedString = request.getParameter("discontinued").trim();
        String companyIdString = request.getParameter("companyId");

        Computer newComputer = null;

        try {
            newComputer = createComputerFromParameters(request, response, name, introducedString, discontinuedString,
                    companyIdString);
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
            request.setAttribute("error", e.getMessage());
        }

        getServletContext().getRequestDispatcher("/Dashboard").forward(request, response);
    }

}
