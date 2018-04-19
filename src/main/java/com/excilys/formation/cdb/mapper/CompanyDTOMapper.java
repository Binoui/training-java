package com.excilys.formation.cdb.mapper;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;

public class CompanyDTOMapper {

    public static CompanyDTO createCompanyDTO(Company company) {
        CompanyDTO caDto = new CompanyDTO();
        caDto.setId(company.getId());
        caDto.setName(company.getName());

        return caDto;
    }

    public static Company createCompanyFromDto(CompanyDTO companyDto) {
        CompanyBuilder companyBuilder = new CompanyBuilder();
        companyBuilder.withId(companyDto.getId());
        companyBuilder.withName(companyDto.getName());
        return companyBuilder.build();
    }

}
