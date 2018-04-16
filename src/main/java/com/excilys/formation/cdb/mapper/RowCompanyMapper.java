package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;

public class RowCompanyMapper implements RowMapper<Company> {
    @Override
    public Company mapRow(ResultSet rs, int row) throws SQLException {
        Long id = rs.getLong("ca_id");
        String name = rs.getString("ca_name");
        return new CompanyBuilder().withId(id).withName(name).build();
    }
}
