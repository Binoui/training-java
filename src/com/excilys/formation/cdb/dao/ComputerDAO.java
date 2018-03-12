package com.excilys.formation.cdb.dao;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComputerDAO implements IComputerDAO {
	
	public List<Computer> listComputers() {
		ArrayList<Computer> computers = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn = DatabaseConnection.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from computer;");
			
			while (rs.next()) {
				computers.add(ComputerMapper.createComputer(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(rs, st, conn);
		}
		
		return computers;
	}

	@Override
	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateComputer(Long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteComputer(Long id) {
		// TODO Auto-generated method stub
		
	}
}
