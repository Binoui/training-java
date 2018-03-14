package com.excilys.formation.cdb.mapper;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Company;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public enum ComputerMapper {
	INSTANCE;
	
	public CompanyMapper companyMapper = CompanyMapper.INSTANCE;
	
	public Computer createComputer(ResultSet rs) throws SQLException {
		Computer c = new Computer();

		c.setId(rs.getLong("id"));

		String name = rs.getString("name");
		c.setName(name);
		
		Company company = companyMapper.createCompany(rs);
		c.setCompany(company);

		Date introduced = rs.getDate("introduced");
		if (introduced != null)
			c.setIntroduced(introduced.toLocalDate());

		Date discontinued = rs.getDate("discontinued");
		if (discontinued != null)
			c.setDiscontinued(discontinued.toLocalDate());
		
		return c;
	}
}
