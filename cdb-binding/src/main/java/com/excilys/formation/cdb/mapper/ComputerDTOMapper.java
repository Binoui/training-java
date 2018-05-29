package com.excilys.formation.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.validators.InvalidDatesException;

public class ComputerDTOMapper {
    
    private ComputerDTOMapper() {}
    
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
            computerDto.setCompanyDTO(CompanyDTOMapper.createCompanyDTO(computer.getCompany().get()));
        }

        return computerDto;
    }

    public static Computer createComputerFromDto(ComputerDTO computerDto) throws InvalidDatesException {
        ComputerBuilder computerBuilder = new ComputerBuilder();

        computerBuilder.withId(computerDto.getId());
        computerBuilder.withName(computerDto.getName());

        try {
            if (StringUtils.isNotBlank(computerDto.getIntroduced())) {
                computerBuilder.withIntroduced(LocalDate.parse(computerDto.getIntroduced()));
            }

            if (StringUtils.isNotBlank(computerDto.getDiscontinued())) {
                computerBuilder.withDiscontinued(LocalDate.parse(computerDto.getDiscontinued()));
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDatesException("Incorrect Date Format");
        }

        if ((computerDto.getCompanyDTO() != null) && (computerDto.getCompanyDTO().getId() != 0))

        {
            computerBuilder.withCompany(CompanyDTOMapper.createCompanyFromDto(computerDto.getCompanyDTO()));
        }

        return computerBuilder.build();
    }
}
