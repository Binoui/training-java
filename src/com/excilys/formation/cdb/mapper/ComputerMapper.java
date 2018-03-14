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

		c.setId(rs.getLong("cu_id"));

		String name = rs.getString("cu_name");
		c.setName(name);
		
		Company company = companyMapper.createCompany(rs);
		c.setCompany(company);

		Date introduced = rs.getDate("cu_introduced");
		if (introduced != null)
			c.setIntroduced(introduced.toLocalDate());

		Date discontinued = rs.getDate("cu_discontinued");
		if (discontinued != null)
			c.setDiscontinued(discontinued.toLocalDate());
		
		return c;
	}
}
