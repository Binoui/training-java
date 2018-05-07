package com.excilys.formation.cdb.controllers.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.dto.ComputerDTO;

public interface ComputerRestController {

    void initBinder(WebDataBinder binder);

    ResponseEntity<ComputerDTO> getComputerById(long id);

    List<ComputerDTO> getComputers();

    ResponseEntity<List<ComputerDTO>> getComputerPage(int page, int size);

    ResponseEntity<List<ComputerDTO>> getComputerPageSearch(int page, int size, String search);

    ResponseEntity<List<ComputerDTO>> getComputerPageSorted(int page, int size, SortableComputerColumn column,
            boolean ascending);

    ResponseEntity<List<ComputerDTO>> getComputerPageSortedSearch(int page, int size, String search,
            SortableComputerColumn column, boolean ascending);

    ResponseEntity<String> createComputer(ComputerDTO computerDTO);

    ResponseEntity<String> editComputer(ComputerDTO computerDto);

    ResponseEntity<String> deleteComputer(long id);

    ResponseEntity<Integer> getComputerPageCount(int size);

    ResponseEntity<Integer> getComputerPageSearchCount(int size, String search);

}