package com.excilys.formation.cdb.dao;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
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

		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from computer;")) {

			while (rs.next()) {
				computers.add(ComputerMapper.createComputer(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return computers;
	}

	public List<Computer> listComputers(int pageNumber, int pageSize) throws IndexOutOfBoundsException {
		ArrayList<Computer> computers = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;

		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();) {

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
			DatabaseConnection.INSTANCE.closeConnection(rs, st);
		}

		return computers;
	}

	public int getPageCount(int pageSize) {
		int pageCount = 0;

		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
				PreparedStatement st = conn.prepareStatement("select count(*) from computer;");
				ResultSet rs = st.executeQuery()) {

			rs.next();
			int computerCount = rs.getInt(1);
			pageCount = computerCount / pageSize;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pageCount;
	}

	@Override
	public void createComputer(Computer c) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try (Connection conn = DatabaseConnection.INSTANCE.getConnection()) {

			st = conn.prepareStatement("insert into computer values (?, ?, ?, ?, ?);");
			st.setLong(1, c.getId());
			st.setString(2, c.getName());
			st.setDate(3, Date.valueOf(c.getIntroduced()));
			st.setDate(4, Date.valueOf(c.getDiscontinued()));
			st.setLong(5, c.getCompanyId());

			rs = st.executeQuery();

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(rs, st);
		}
	}

	@Override
	public void updateComputer(Computer c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteComputer(Computer c) {
		// TODO Auto-generated method stub

	}
}
