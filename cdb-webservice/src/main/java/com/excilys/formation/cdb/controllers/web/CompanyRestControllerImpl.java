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
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.services.CompanyService;

@RestController
public class CompanyRestControllerImpl implements CompanyRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRestControllerImpl.class);

    private CompanyService companyService;

    public CompanyRestControllerImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    @GetMapping(value = "/company/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable long id) {
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
    @GetMapping(value = "/companies")
    public List<CompanyDTO> getCompanies() {
        return companyService.getListCompanies().stream().map((Company c) -> CompanyDTOMapper.createCompanyDTO(c))
                .collect(Collectors.toList());
    }
    
    @Override
    @GetMapping(value = "/companies/size/{size}/count")
    public ResponseEntity<Integer> getCompanyPageCount(@PathVariable int size) {
        return new ResponseEntity<>(companyService.getListCompaniesPageCount(size), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/companies/page/{page}/size/{size}")
    public ResponseEntity<List<CompanyDTO>> getCompanyPage(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(companyService.getListCompanies(page, size)
                .stream().map(c -> CompanyDTOMapper.createCompanyDTO(c)).collect(Collectors.toList()), HttpStatus.OK);
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
