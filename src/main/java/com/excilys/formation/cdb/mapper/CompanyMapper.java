package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.model.Company;

public enum CompanyMapper {
	INSTANCE;

	public Company createCompany(ResultSet rs) throws SQLException {
		Company c = new Company();

		c.setId(rs.getLong("ca_id"));
		String name = rs.getString("ca_name");
		c.setName(name);
		
		return c;
	}
}
