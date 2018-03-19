package com.excilys.formation.cdb.dao;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.utils.DatabaseConnection;

import org.slf4j.Logger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;

public enum ComputerDAO implements IComputerDAO {
	INSTANCE;

	private static final String SELECT_ALL_COMPUTERS = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id;";
	private static final String SELECT_ALL_COMPUTERS_PAGE = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id order by cu_id limit ? offset ?;";
	private static final String SELECT_COUNT_COMPUTERS = "select count(cu_id) from computer;";
	private static final String SELECT_COMPUTER = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id where cu_id = ?;";
	private static final String INSERT_COMPUTER = "insert into computer (cu_name, cu_introduced, cu_discontinued, ca_id) values (?, ?, ?, ?);";
	private static final String UPDATE_COMPUTER = "update computer set cu_name = ?, cu_introduced = ?, cu_discontinued = ?, ca_id = ? where cu_id = ?;";
	private static final String DELETE_COMPUTER = "delete from computer where cu_id = ?;";

	private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private ComputerMapper mapper = ComputerMapper.INSTANCE;
	private DatabaseConnection dbConn = DatabaseConnection.INSTANCE;

	public List<Computer> getListComputers() {
		logger.debug("list computers");
		ArrayList<Computer> computers = new ArrayList<>();

		try (Connection conn = dbConn.getConnection();
				Statement st = conn.createStatement();
				ResultSet computerList = st.executeQuery(SELECT_ALL_COMPUTERS)) {

			while (computerList.next()) {
				computers.add(mapper.createComputer(computerList));
			}

		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}

		return computers;
	}

	public List<Computer> getListComputers(int pageNumber, int pageSize) throws IndexOutOfBoundsException {
		logger.debug("list computers");
		ArrayList<Computer> computers = new ArrayList<>();

		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement(SELECT_ALL_COMPUTERS_PAGE);) {

			st.setInt(1, pageSize);
			st.setInt(2, pageSize * pageNumber);

			try (ResultSet rs = st.executeQuery()) {

				while (rs.next()) {
					computers.add(mapper.createComputer(rs));
				}

				if (computers.isEmpty()) {
					throw new IndexOutOfBoundsException("Given page number is greater than page count");
				}
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}

		return computers;
	}

	public int getListComputersPageCount(int pageSize) {
		int pageCount = 0;

		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement(SELECT_COUNT_COMPUTERS);
				ResultSet rs = st.executeQuery()) {

			rs.next();
			int computerCount = rs.getInt(1);
			pageCount = computerCount / pageSize;

		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}

		return pageCount;
	}
	
	public Optional<Computer> getComputer(Computer computer) {
		Computer c = null;

		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement(SELECT_COMPUTER);) {
			
			st.setLong(1, computer.getId());
			
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					c = mapper.createComputer(rs);
				}
			}
			
		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
		
		return Optional.ofNullable(c);
	}

	@Override
	public Long createComputer(Computer c){
		logger.debug("create computer");
		Long key = null;
		
		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement(INSERT_COMPUTER, Statement.RETURN_GENERATED_KEYS);) {

			populateStatementFromComputer(c, st);
			st.executeUpdate();
			
			try (ResultSet rs = st.getGeneratedKeys()) {
				rs.next();
				key = rs.getLong(1);
			}

		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
		
		return key;
	}

	@Override
	public void updateComputer(Computer c) {
		logger.debug("update computer");
		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement(UPDATE_COMPUTER);) {

			populateStatementFromComputer(c, st);
			st.setLong(5, c.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			logger.debug(e.getMessage());
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

		if (c.getCompany() != null && c.getCompany().getId() != null)
			st.setLong(4, c.getCompany().getId());
		else
			st.setNull(4, java.sql.Types.BIGINT);
	}

	@Override
	public void deleteComputer(Computer c) {
		logger.debug("delete computer");
		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement(DELETE_COMPUTER)) {

			st.setLong(1, c.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}
}
