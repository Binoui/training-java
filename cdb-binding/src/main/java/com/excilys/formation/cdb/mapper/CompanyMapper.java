package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;

public class CompanyMapper {
    
    private CompanyMapper() {}

    public static Company createCompany(ResultSet rs) throws SQLException {
        Long id = rs.getLong("ca_id");

        if ((id == null) || (id == 0)) {
            return null;
        }

        String name = rs.getString("ca_name");
        return new CompanyBuilder().withId(id).withName(name).build();
    }
}
