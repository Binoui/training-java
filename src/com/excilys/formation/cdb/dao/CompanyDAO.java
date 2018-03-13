package com.excilys.formation.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.utils.DatabaseConnection;

public enum CompanyDAO implements ICompanyDAO {
	INSTANCE;

	private CompanyMapper mapper = CompanyMapper.INSTANCE;
	private DatabaseConnection dbConn = DatabaseConnection.INSTANCE;
	
	@Override
	public List<Company> getListCompanies() {
		ArrayList<Company> companies = new ArrayList<>();

		try (Connection conn = dbConn.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from company;")) {

			while (rs.next()) {
				companies.add(mapper.createCompany(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return companies;
	}

	public List<Company> getListCompanies(int pageNumber, int pageSize) throws IndexOutOfBoundsException {
		ArrayList<Company> companies = new ArrayList<>();
		ResultSet rs = null;

		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement("select * from company limit ? offset ?;");) {

			st.setInt(1, pageSize);
			st.setInt(2, pageSize * pageNumber);
			rs = st.executeQuery();

			while (rs.next()) {
				companies.add(mapper.createCompany(rs));
			}

			if (companies.isEmpty()) {
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

		return companies;
	}

	public int getPageCount(int pageSize) {
		int pageCount = 0;

		try (Connection conn = dbConn.getConnection();
				PreparedStatement st = conn.prepareStatement("select count(*) from company;");
				ResultSet rs = st.executeQuery()) {

			rs.next();
			int companyCount = rs.getInt(1);
			pageCount = companyCount / pageSize;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pageCount;
	}
	
	public Company getCompany(Long id) {
		
		Company c = null;
		ResultSet rs = null;
		try (Connection conn = dbConn.getConnection(); 
				PreparedStatement st = conn.prepareStatement("select * from company where id = ?"); ) {
			
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				c = mapper.createCompany(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return c;
	}
	
	public Company getCompany(Company c) {
		return getCompany(c.getId());
	}

}
