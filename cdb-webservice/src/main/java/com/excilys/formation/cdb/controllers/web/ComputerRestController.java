package com.excilys.formation.cdb.controllers.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.excilys.formation.cdb.dto.ComputerDTO;

public interface ComputerRestController {

    ResponseEntity<ComputerDTO> getComputerById(long id);

    List<ComputerDTO> getComputers();

    ResponseEntity<String> createComputer(ComputerDTO computerDTO);

}