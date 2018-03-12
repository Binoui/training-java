package com.excilys.formation.cdb.dao;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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
			conn = DatabaseConnection.INSTANCE.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from computer;");
			
			while (rs.next()) {
				computers.add(ComputerMapper.createComputer(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(rs, st, conn);
		}
		
		return computers;
	}

	public List<Computer> listComputers(int pageNumber, int pageSize) throws IndexOutOfBoundsException {
		ArrayList<Computer> computers = new ArrayList<>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = DatabaseConnection.INSTANCE.getConnection();
			st = conn.prepareStatement("select * from computer limit ? offset ?;");
			st.setInt(1, pageSize);
			st.setInt(2, pageSize * pageNumber);
			rs = st.executeQuery();
			
			while (rs.next()) {
				computers.add(ComputerMapper.createComputer(rs));
			}
			
			if (computers.isEmpty()) {
				throw new IndexOutOfBoundsException("Given page number is greater than page count");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(rs, st, conn);
		}
		
		return computers;
	}
	
	public int getPageCount(int pageSize) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		int pageCount = 0;

		try {
			conn = DatabaseConnection.INSTANCE.getConnection();
			st = conn.prepareStatement("select count(*) from computer;");
			rs = st.executeQuery();
			
			rs.next();
			int computerCount = rs.getInt(1);
			pageCount = computerCount / pageSize;			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(rs, st, conn);
		}
		
		return pageCount;
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
