package com.excilys.formation.cdb.controllers.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.excilys.formation.cdb.dto.CompanyDTO;

public interface CompanyRestController {

    ResponseEntity<CompanyDTO> getCompany(long id);

    List<CompanyDTO> getCompanies();

}