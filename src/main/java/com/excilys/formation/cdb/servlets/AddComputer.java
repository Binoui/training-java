package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final CompanyDTOMapper companyMapper = CompanyDTOMapper.INSTANCE;
	private static final CompanyService companyService = CompanyService.INSTANCE;
	private static final ComputerService computerService = ComputerService.INSTANCE;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    
        List<CompanyDTO> listCompanies = new LinkedList<>();
        companyService.getListCompanies().forEach(company -> listCompanies.add(companyMapper.createCompanyDTO(company)));
        request.setAttribute("companies", listCompanies);

        getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    String name = request.getParameter("computerName").trim();
        String introducedString = request.getParameter("introduced").trim();
        String discontinuedString = request.getParameter("discontinued").trim();
        String companyIdString = request.getParameter("companyId");

	    if (! name.isEmpty() ) {
            ComputerBuilder computerBuilder = new ComputerBuilder().withName(name);
            
            if (introducedString != null && !introducedString.isEmpty()) {
                computerBuilder.withIntroduced(LocalDate.parse(introducedString));
            }
     
            if (discontinuedString != null && !discontinuedString.isEmpty()) {
                computerBuilder.withDiscontinued(LocalDate.parse(discontinuedString));
            }
            
            if (companyIdString != null && !companyIdString.isEmpty()) {
                Long companyId = Long.parseLong(companyIdString);
                computerBuilder.withCompany(new CompanyBuilder().withId(companyId).build());
            }
            
    	    try {
                computerService.createComputer(computerBuilder.build());
            } catch (IncorrectValidationException e) {
                e.printStackTrace();
            }
        } else {
            LoggerFactory.getLogger(AddComputer.class).debug("Couldn't add computer, name was empty");
	    }

        getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
	}
	
	
}
