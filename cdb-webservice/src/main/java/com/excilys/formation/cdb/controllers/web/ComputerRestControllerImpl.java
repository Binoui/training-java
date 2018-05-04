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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;
import com.excilys.formation.cdb.validators.IncorrectValidationException;

@RestController
public class ComputerRestControllerImpl implements ComputerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerRestControllerImpl.class);

    private ComputerService computerService;

    public ComputerRestControllerImpl(ComputerService computerService) {
        this.computerService = computerService;
    }

    @Override
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

    @Override
    @GetMapping(value = "/computers")
    public List<ComputerDTO> getComputers() {
        return computerService.getListComputers().stream().map((Computer c) -> ComputerDTOMapper.createComputerDTO(c)).collect(Collectors.toList());
    }

    @Override
    @PostMapping(value = "/computer")
    public ResponseEntity<String> createComputer(@RequestBody ComputerDTO computerDTO) {

        ResponseEntity<String> response;
        try {
            computerService.createComputer(ComputerDTOMapper.createComputerFromDto(computerDTO));
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException | IncorrectValidationException e) {
            LOGGER.debug("couldn't create computer {}", e);
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }
    
    @PutMapping(value = "/computer")
    private ResponseEntity<String> editComputer(@RequestBody ComputerDTO computerDto) {

        ResponseEntity<String> response;
        try {
            computerService.updateComputer(ComputerDTOMapper.createComputerFromDto(computerDto));
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException | IncorrectValidationException e) {
            LOGGER.debug("Cannot edit computer {}", e);
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }
    
    @DeleteMapping(value = "/delete")
    private ResponseEntity<String> deleteComputer(@RequestBody List<Long> idsToDelete) {

        computerService.deleteComputers(idsToDelete);
        return modelAndView;
    }
}
