package com.excilys.formation.cdb.mapper;

import java.time.format.DateTimeFormatter;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerDTOMapper {
    INSTANCE;

    public ComputerDTO createComputerDTO(Computer computer) {
        ComputerDTO caDto = new ComputerDTO();

        caDto.setId(computer.getId());
        caDto.setName(computer.getName());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        caDto.setIntroduced(computer.getIntroduced().format(dateFormatter));
        caDto.setDiscontinued(computer.getDiscontinued().format(dateFormatter));
        caDto.setCompanyName(computer.getCompany().getName());

        return caDto;
    }
}
