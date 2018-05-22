package com.excilys.formation.cdb.controllers.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.dto.ComputerDTO;

public interface ComputerRestController {

    ResponseEntity<String> createComputer(ComputerDTO computerDTO);

    ResponseEntity<String> deleteComputer(long id);

    ResponseEntity<String> editComputer(ComputerDTO computerDto);

    ResponseEntity<ComputerDTO> getComputerById(long id);

    ResponseEntity<Integer> getComputerPageCountSearch(int size, String search);

    ResponseEntity<List<ComputerDTO>> getComputerPageSortedSearch(int page, int size, String search,
            SortableComputerColumn column, boolean ascending);

    List<ComputerDTO> getComputers();

    void initBinder(WebDataBinder binder);

}