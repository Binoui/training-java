package com.excilys.formation.cdb.controllers.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ServiceException;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api
public class CompanyRestControllerImpl implements CompanyRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRestControllerImpl.class);

    private CompanyService companyService;

    public CompanyRestControllerImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    @ApiOperation(
            value = "Find company from an Id",
            response = CompanyDTO.class
        )
    @GetMapping(value = "/company/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@ApiParam( value = "Page to fetch", required = true ) @PathVariable long id) {
        
        ResponseEntity<CompanyDTO> response;
        Optional<Company> optionalCompany = companyService.getCompany(id);
        if (optionalCompany.isPresent()) {
            response = new ResponseEntity<CompanyDTO>(CompanyDTOMapper.createCompanyDTO(optionalCompany.get()),
                    HttpStatus.OK);
        } else {
            response = new ResponseEntity<CompanyDTO>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    @ApiOperation(
            value = "List all companies",
            response = CompanyDTO.class,
            responseContainer = "List"
        )
    @GetMapping(value = "/companies")
    public List<CompanyDTO> getCompanies() {
        return companyService.getListCompanies().stream().map((Company c) -> CompanyDTOMapper.createCompanyDTO(c))
                .collect(Collectors.toList());
    }
    
    @Override
    @ApiOperation(
            value = "Get the count of company",
            response = Integer.class
        )
    @GetMapping(value = "/companies/size/{size}/count")
    public ResponseEntity<Integer> getCompanyPageCount(@PathVariable int size) {
        return new ResponseEntity<>(companyService.getListCompaniesPageCount(size), HttpStatus.OK);
    }

    @Override
    @ApiOperation(
            value = "List companies with page and size of the page",
            response = CompanyDTO.class,
            responseContainer = "List"
        )
    @GetMapping(value = "/companies/page/{page}/size/{size}")
    public ResponseEntity<List<CompanyDTO>> getCompanyPage(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(companyService.getListCompanies(page, size)
                .stream().map(c -> CompanyDTOMapper.createCompanyDTO(c)).collect(Collectors.toList()), HttpStatus.OK);
    }
    
    @Override
    @ApiOperation(
            value = "Update company from an Id",
            response = CompanyDTO.class
        )
    @PutMapping(value = "/company")
    public ResponseEntity<String> editCompany(@RequestBody CompanyDTO companyDto) {

        ResponseEntity<String> response;
        companyService.updateCompany(CompanyDTOMapper.createCompanyFromDto(companyDto));
        response = new ResponseEntity<>(HttpStatus.OK);

        return response;
    }
    
    @Override
    @DeleteMapping(value = "/computer/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable long id) {
        ResponseEntity<String> response;
        companyService.deleteCompany(id);
        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }
}
