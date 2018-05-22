package com.excilys.formation.cdb.controllers.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.dao.SortableCompanyColumn;
import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.services.CompanyService;

@RestController
@CrossOrigin
public class CompanyRestControllerImpl implements CompanyRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRestControllerImpl.class);

    private CompanyService companyService;

    public CompanyRestControllerImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    @DeleteMapping(value = "/company/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable long id) {
        ResponseEntity<String> response;
        companyService.deleteCompany(id);
        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @Override
    @PutMapping(value = "/company")
    public ResponseEntity<String> editCompany(@RequestBody CompanyDTO companyDto) {

        ResponseEntity<String> response;
        companyService.updateCompany(CompanyDTOMapper.createCompanyFromDto(companyDto));
        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @Override
    @GetMapping(value = "/companies")
    public List<CompanyDTO> getCompanies() {
        return companyService.getListCompanies().stream().map((Company c) -> CompanyDTOMapper.createCompanyDTO(c))
                .collect(Collectors.toList());
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
    @GetMapping(value = "/company/{id}/computers")
    public List<ComputerDTO> getCompanyComputers(@PathVariable int id) {
        return companyService.getCompanyComputers(id).stream().map(c -> ComputerDTOMapper.createComputerDTO(c))
                .collect(Collectors.toList());
    }
    
    @Override
    @GetMapping(value = "/companies/page")
    public ResponseEntity<List<CompanyDTO>> getCompanyPageSortedSearch(@RequestParam int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "ID") SortableCompanyColumn column,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return new ResponseEntity<>(companyService.getListCompanies(page, size, column, ascending, search).stream()
                .map(c -> CompanyDTOMapper.createCompanyDTO(c)).collect(Collectors.toList()), HttpStatus.OK);
    }


    @Override
    @GetMapping(value = "/companies/size/{size}/count")
    public ResponseEntity<Integer> getCompanyPageCount(@PathVariable int size) {
        return new ResponseEntity<>(companyService.getListCompaniesPageCount(size), HttpStatus.OK);
    }
}
