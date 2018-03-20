package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;

public enum ComputerMapper {
    INSTANCE;

    public static CompanyMapper companyMapper = CompanyMapper.INSTANCE;

    public Computer createComputer(ResultSet rs) throws SQLException {

        Long id = rs.getLong("cu_id");

        String name = rs.getString("cu_name");

        Company company = companyMapper.createCompany(rs);
        ComputerBuilder builder = new ComputerBuilder().withId(id).withName(name).withCompany(company);

        Date introduced = rs.getDate("cu_introduced");
        if (introduced != null) {
            builder.withIntroduced(introduced.toLocalDate());
        }

        Date discontinued = rs.getDate("cu_discontinued");
        if (discontinued != null) {
            builder.withDiscontinued(discontinued.toLocalDate());
        }

        return builder.build();
    }
}
