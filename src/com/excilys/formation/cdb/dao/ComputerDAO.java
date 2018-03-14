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
import java.util.ArrayList;
import java.util.List;

public enum ComputerDAO implements IComputerDAO {
	INSTANCE;

	private ComputerMapper mapper = ComputerMapper.INSTANCE;
	private DatabaseConnection dbConn = DatabaseConnection.INSTANCE;
	
	public List<Computer> getListComputers() {
		ArrayList<Computer> computers = new ArrayList<>();

		try (Connection conn = dbConn.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, company.id as company_id, company.name as company_name from computer left join company on computer.id = company.id")) {

			while (rs.next()) {
				computers.add(mapper.createComputer(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return computers;
	}

	public List<Computer> getListComputers(int pageNumber, int pageSize) throws IndexOutOfBoundsException {
		ArrayList<Computer> computers = new ArrayList<>();
		ResultSet rs = null;

		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement("select computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, company.id as company_id, company.name as company_name from computer left join company on computer.id = company.id limit ? offset ?;");) {

			st.setInt(1, pageSize);
			st.setInt(2, pageSize * pageNumber);
			rs = st.executeQuery();

			while (rs.next()) {
				computers.add(mapper.createComputer(rs));
			}

			if (computers.isEmpty()) {
				throw new IndexOutOfBoundsException("Given page number is greater than page count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return computers;
	}

	public int getPageCount(int pageSize) {
		int pageCount = 0;

		try (Connection conn = dbConn.getConnection();
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
	public void createComputer(Computer c){

		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement(
						"insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?);");) {

			populateStatementFromComputer(c, st);
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateComputer(Computer c) {

		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement(
						"update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?");) {

			populateStatementFromComputer(c, st);
			st.setLong(5, c.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void populateStatementFromComputer(Computer c, PreparedStatement st) throws SQLException {
		st.setString(1, c.getName());

		if (c.getIntroduced() != null)
			st.setDate(2, Date.valueOf(c.getIntroduced()));
		else
			st.setNull(2, java.sql.Types.DATE);

		if (c.getDiscontinued() != null)
			st.setDate(3, Date.valueOf(c.getDiscontinued()));
		else
			st.setNull(3, java.sql.Types.DATE);

		if (c.getCompany() != null)
			st.setLong(4, c.getCompany().getId());
		else
			st.setNull(4, java.sql.Types.BIGINT);
	}

	@Override
	public void deleteComputer(Computer c) {

		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement("delete from computer where id = ?")) {

			st.setLong(1, c.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
