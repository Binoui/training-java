package com.excilys.formation.cdb.controllers.web;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.excilys.formation.cdb.dao.SortableCompanyColumn;
import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;

public interface CompanyRestController {

    ResponseEntity<String> deleteCompany(long id);

    ResponseEntity<String> editCompany(CompanyDTO companyDto);

    List<CompanyDTO> getCompanies();

    ResponseEntity<CompanyDTO> getCompany(long id);

    List<ComputerDTO> getCompanyComputers(int idCompany);

    ResponseEntity<Integer> getCompanyPageCount(int size);

    ResponseEntity<List<CompanyDTO>> getCompanyPageSortedSearch(int page, int size, String search,
            SortableCompanyColumn column, boolean ascending);

    ResponseEntity<String> createCompany(String name);

    ResponseEntity<Integer> getCompanyPageCountSearch(int size, String search);

}