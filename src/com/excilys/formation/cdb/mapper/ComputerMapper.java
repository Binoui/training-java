package com.excilys.formation.cdb.mapper;

import com.excilys.formation.cdb.model.Computer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComputerMapper {
	public static Computer createComputer(ResultSet rs) throws SQLException {
		Computer c = new Computer();

		c.setId(rs.getLong("id"));

		String name = rs.getString("name");
		if (name != null)
			c.setName(name);
		
		Long company_id = rs.getLong("company_id");
		if (company_id != null)
			c.setCompany(company_id);

		Date introduced = rs.getDate("introduced");
		if (introduced != null)
			c.setIntroduced(introduced.toLocalDate());

		Date discontinued = rs.getDate("discontinued");
		if (discontinued != null)
			c.setDiscontinued(discontinued.toLocalDate());
		
		return c;
	}
}
