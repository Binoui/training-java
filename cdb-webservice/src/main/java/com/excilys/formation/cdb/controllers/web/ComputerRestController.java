package com.excilys.formation.cdb.controllers.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

@RestController
public class ComputerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerRestController.class);

    private ComputerService computerService;

    public ComputerRestController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping(value = "/computer/{id}")
    public ResponseEntity<ComputerDTO> getComputerById(@PathVariable long id) {
        ResponseEntity<ComputerDTO> response;
        Optional<Computer> optionalComputer = computerService.getComputer(id);
        if (optionalComputer.isPresent()) {
            response = new ResponseEntity<ComputerDTO>(ComputerDTOMapper.createComputerDTO(optionalComputer.get()),
                    HttpStatus.OK);
        } else {
            response = new ResponseEntity<ComputerDTO>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping(value = "/computers")
    public List<ComputerDTO> getComputers() {
        return computerService.getListComputers().stream().map((Computer c) -> ComputerDTOMapper.createComputerDTO(c)).collect(Collectors.toList());
    }

    @PostMapping(value = "/computer/add")
    public ResponseEntity<String> createComputer(@RequestBody ComputerDTO computerDTO) {

        ResponseEntity<String> response;
        Computer computer;
        try {
            computer = ComputerDTOMapper.createComputerFromDto(computerDTO);
            computerService.createComputer(computer);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException | IncorrectValidationException e) {
            LOGGER.debug("couldn't create computer {}", e);
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
