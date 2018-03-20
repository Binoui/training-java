package com.excilys.formation.cdb.mapper;

import java.time.format.DateTimeFormatter;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerDTOMapper {
    INSTANCE;

    public ComputerDTO createComputerDTO(Computer computer) {
        ComputerDTO caDto = new ComputerDTO();

        caDto.setId(computer.getId());
        
        if (computer.getName() != null) {
            caDto.setName(computer.getName());
        }
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (computer.getIntroduced() != null) {
            caDto.setIntroduced(computer.getIntroduced().format(dateFormatter));
        }
        
        if (computer.getDiscontinued() != null) {
            caDto.setDiscontinued(computer.getDiscontinued().format(dateFormatter));
        }
        
        if (computer.getCompany() != null) {
            caDto.setCompanyName(computer.getCompany().getName());
        }

        return caDto;
    }
}
