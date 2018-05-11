package com.excilys.formation.cdb.controllers.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.CompanyService;
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
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SortableComputerColumn.class, new SortableComputerColumnEnumConverter());
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
        return computerService.getListComputers().stream().map((Computer c) -> ComputerDTOMapper.createComputerDTO(c))
                .collect(Collectors.toList());
    }
    
    @Override
    @GetMapping(value = "/computers/size/{size}/count")
    public ResponseEntity<Integer> getComputerPageCount(@PathVariable int size) {
        return new ResponseEntity<>(computerService.getListComputersPageCount(size), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/computers/page/{page}/size/{size}")
    public ResponseEntity<List<ComputerDTO>> getComputerPage(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(computerService.getListComputers(page, size, SortableComputerColumn.ID, true)
                .stream().map(c -> ComputerDTOMapper.createComputerDTO(c)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/computers/size/{size}/search/{search}")
    public ResponseEntity<Integer> getComputerPageSearchCount(@PathVariable int page, @PathVariable String search) {
        return new ResponseEntity<>(computerService.getListComputersPageCount(page, search), HttpStatus.OK);
    }
    
    @Override
    @GetMapping(value = "/computers/page/{page}/size/{size}/search/{search}")
    public ResponseEntity<List<ComputerDTO>> getComputerPageSearch(@PathVariable int page, @PathVariable int size,
            @PathVariable String search) {
        return new ResponseEntity<>(
                computerService.getListComputers(page, size, SortableComputerColumn.ID, true, search).stream()
                        .map(c -> ComputerDTOMapper.createComputerDTO(c)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/computers/page/{page}/size/{size}/column/{column}/ascending/{ascending}")
    public ResponseEntity<List<ComputerDTO>> getComputerPageSorted(@PathVariable int page, @PathVariable int size,
            @PathVariable SortableComputerColumn column, @PathVariable boolean ascending) {
        return new ResponseEntity<>(computerService.getListComputers(page, size, column, ascending).stream()
                .map(c -> ComputerDTOMapper.createComputerDTO(c)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/computers/page/{page}/size/{size}/search/{search}/column/{column}/ascending/{ascending}")
    public ResponseEntity<List<ComputerDTO>> getComputerPageSortedSearch(@PathVariable int page, @PathVariable int size,
            @PathVariable String search, @PathVariable SortableComputerColumn column, @PathVariable boolean ascending) {
        return new ResponseEntity<>(computerService.getListComputers(page, size, column, ascending, search).stream()
                .map(c -> ComputerDTOMapper.createComputerDTO(c)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/computer")
    public ResponseEntity<String> createComputer(@RequestBody ComputerDTO computerDTO) {

        ResponseEntity<String> response;
        try {
            computerService.createComputer(ComputerDTOMapper.createComputerFromDto(computerDTO));
            response = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ServiceException | IncorrectValidationException e) {
            LOGGER.debug("couldn't create computer {}", e);
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @Override
    @PutMapping(value = "/computer")
    public ResponseEntity<String> editComputer(@RequestBody ComputerDTO computerDto) {

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

    @Override
    @DeleteMapping(value = "/computer/{id}")
    public ResponseEntity<String> deleteComputer(@PathVariable long id) {
        ResponseEntity<String> response;

        try {
            CompanyService.deleteComputer(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

}
