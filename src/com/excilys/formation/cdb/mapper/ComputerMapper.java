package com.excilys.formation.cdb.mapper;

import com.excilys.formation.cdb.model.Computer;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComputerMapper {
	public static Computer createComputer(ResultSet rs) throws SQLException {
		Computer c = new Computer();
		c.setId(rs.getLong("id"));
		c.setName(rs.getString("name"));
		c.setCompany(rs.getLong("company_id"));
		c.setIntroduced(rs.getDate("introducted").toLocalDate());
		c.setDiscontinued(rs.getDate("discontinued").toLocalDate());
		
		return c;
	}
}
