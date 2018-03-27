package com.excilys.formation.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.utils.DatabaseConnection;

public enum CompanyDAO implements ICompanyDAO {
    INSTANCE;

    private static final String SELECT_COMPANIES = "select ca_id, ca_name from company;";
    private static final String SELECT_COMPANIES_PAGE = "select ca_id, ca_name from company order by ca_id limit ? offset ? ;";
    private static final String SELECT_COUNT_COMPANIES = "select count(ca_id) from company;";
    private static final String SELECT_COMPANY = "select ca_id, ca_name from company where ca_id = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private static CompanyMapper mapper = CompanyMapper.INSTANCE;
    private static DatabaseConnection dbConn = DatabaseConnection.INSTANCE;

    public Optional<Company> getCompany(Company c) throws DAOException {
        return getCompany(c.getId());
    }

    public Optional<Company> getCompany(Long id) throws DAOException {
        LOGGER.info("get company");
        Company c = null;
        ResultSet rs = null;
        try (Connection conn = dbConn.getConnection(); PreparedStatement st = conn.prepareStatement(SELECT_COMPANY);) {

            if (id != null) {
                st.setLong(1, id);
            } else {
                st.setNull(1, java.sql.Types.BIGINT);
            }

            rs = st.executeQuery();
            if (rs.next()) {
                c = mapper.createCompany(rs);
            }

        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            throw new DAOException("Couldn't find company with ID : " + c.getId());
        }

        return Optional.ofNullable(c);
    }

    @Override
    public List<Company> getListCompanies() throws DAOException {
        LOGGER.info("get list companies");
        ArrayList<Company> companies = new ArrayList<>();

        try (Connection conn = dbConn.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(SELECT_COMPANIES)) {

            while (rs.next()) {
                companies.add(mapper.createCompany(rs));
            }

        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            throw new DAOException("Couldn't fetch companies list"); 
        }

        return companies;
    }

    @Override
    public List<Company> getListCompanies(final int pageNumber, final int pageSize) throws DAOException, IndexOutOfBoundsException {
        LOGGER.info("get list companies");
        ArrayList<Company> companies = new ArrayList<>();
        ResultSet rs = null;
        try (Connection conn = dbConn.getConnection();
                PreparedStatement st = conn.prepareStatement(SELECT_COMPANIES_PAGE);) {

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
            LOGGER.debug(e.getMessage());
            throw new DAOException("Couldn't fetch companies list");
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.debug(e.getMessage());
            }
        }

        return companies;
    }

    public int getListCompaniesPageCount(int pageSize) throws DAOException {
        LOGGER.info("get page count");
        int pageCount = 0;

        try (Connection conn = dbConn.getConnection();
                PreparedStatement st = conn.prepareStatement(SELECT_COUNT_COMPANIES);
                ResultSet rs = st.executeQuery()) {

            rs.next();
            int companyCount = rs.getInt(1);
            pageCount = companyCount / pageSize;

        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            throw new DAOException("Couldn't get companies page count");
        }

        return pageCount;
    }

}
