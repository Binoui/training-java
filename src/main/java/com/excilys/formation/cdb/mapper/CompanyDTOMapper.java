package com.excilys.formation.cdb.mapper;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.model.Company;

public enum CompanyDTOMapper {
    INSTANCE;

    public CompanyDTO createCompanyDTO(Company company) {
        CompanyDTO caDto = new CompanyDTO();

        caDto.setId(company.getId());
        caDto.setName(company.getName());

        return caDto;
    }

}
