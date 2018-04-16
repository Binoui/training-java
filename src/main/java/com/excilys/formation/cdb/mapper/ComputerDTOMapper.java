package com.excilys.formation.cdb.mapper;

import java.time.format.DateTimeFormatter;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Computer;

public class ComputerDTOMapper {

    public static ComputerDTO createComputerDTO(Computer computer) {
        ComputerDTO computerDto = new ComputerDTO();

        computerDto.setId(computer.getId());

        if (computer.getName() != null) {
            computerDto.setName(computer.getName());
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (computer.getIntroduced().isPresent()) {
            computerDto.setIntroduced(computer.getIntroduced().get().format(dateFormatter));
        }

        if (computer.getDiscontinued().isPresent()) {
            computerDto.setDiscontinued(computer.getDiscontinued().get().format(dateFormatter));
        }

        if (computer.getCompany().isPresent()) {
            computerDto.setCompany(CompanyDTOMapper.createCompanyDTO(computer.getCompany().get()));
        }

        return computerDto;
    }
}
