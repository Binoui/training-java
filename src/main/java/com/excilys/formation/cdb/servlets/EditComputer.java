package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

/**
 * Servlet implementation class EditComputer
 */
@WebServlet(name = "EditComputer", urlPatterns = "/EditComputer")
public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final CompanyDTOMapper companyMapper = CompanyDTOMapper.INSTANCE;
    private static final CompanyService companyService = CompanyService.INSTANCE;
    private static final ComputerService computerService = ComputerService.INSTANCE;
    private static final Logger Logger = LoggerFactory.getLogger(EditComputer.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
    }

    private Long convertStringIdToLong(String idString) {
        Long id = (long) 0;
        if ((idString != null) && !idString.isEmpty()) {
            id = Long.parseLong(idString);
        }

        return id;
    }

    private Computer createComputerFromParameters(HttpServletRequest request, HttpServletResponse response, String name,
            String computerIdString, String introducedString, String discontinuedString, String companyIdString)
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
            Logger.error("Cannot edit computer, wrong date format {}", e);
            doGet(request, response);
            return null;
        }

        Long computerId = convertStringIdToLong(computerIdString);
        if (computerId != 0) {
            computerBuilder.withId(computerId);
        }

        Long companyId = convertStringIdToLong(companyIdString);
        if (companyId != 0) {
            computerBuilder.withCompany(new CompanyBuilder().withId(companyId).build());
        }

        Computer computer = computerBuilder.build();
        return computer;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CompanyDTO> listCompanies = new LinkedList<>();

        String computerIdString = request.getParameter("computerId");
        Long computerId = convertStringIdToLong(computerIdString);
        Computer computer;
        Optional<Computer> optionalComputer;
        if (computerId == 0) {
            redirectToDashboardWithError(request, response, "Couldn't find given computer");
            return;
        }

        try {
            computer = new ComputerBuilder().withId(computerId).build();
            optionalComputer = computerService.getComputer(computer);

            if (!optionalComputer.isPresent()) {
                redirectToDashboardWithError(request, response, "Couldn't find given computer");
                return;
            }

            request.setAttribute("computer", ComputerDTOMapper.INSTANCE.createComputerDTO(optionalComputer.get()));

            companyService.getListCompanies()
                    .forEach(company -> listCompanies.add(companyMapper.createCompanyDTO(company)));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        request.setAttribute("companies", listCompanies);

        getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Logger.debug("entering doPost");
        String name = request.getParameter("computerName").trim();
        String computerIdString = request.getParameter("computerId").trim();
        String introducedString = request.getParameter("introduced").trim();
        String discontinuedString = request.getParameter("discontinued").trim();
        String companyIdString = request.getParameter("companyId").trim();

        Computer computer = createComputerFromParameters(request, response, name, computerIdString, introducedString,
                discontinuedString, companyIdString);

        if (computer == null) {
            return;
        }

        try {
            computerService.updateComputer(computer);
        } catch (ServiceException | IncorrectValidationException e) {
            Logger.debug("Cannot edit computer {}", e);
            request.setAttribute("error", e.getMessage());
        }

        getServletContext().getRequestDispatcher("/Dashboard").forward(request, response);
    }

    private void redirectToDashboardWithError(HttpServletRequest request, HttpServletResponse response, String error)
            throws ServletException, IOException {
        Logger.error("error finding computer, redirecting to dashboard");
        request.setAttribute("error", error);
        getServletContext().getRequestDispatcher("/Dashboard").forward(request, response);
    }

}
