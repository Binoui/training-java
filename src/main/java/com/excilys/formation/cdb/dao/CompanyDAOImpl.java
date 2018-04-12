package com.excilys.formation.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;

@Repository("CompanyDAO")
public class CompanyDAOImpl implements CompanyDAO {

    private static final String SELECT_COMPANIES = "select ca_id, ca_name from company;";
    private static final String SELECT_COMPANIES_PAGE = "select ca_id, ca_name from company order by ca_id limit ? offset ? ;";
    private static final String SELECT_COUNT_COMPANIES = "select count(ca_id) from company;";
    private static final String SELECT_COMPANY = "select ca_id, ca_name from company where ca_id = ?";
    private static final String DELETE_COMPANY = "delete from company where ca_id = ?";

    private static final Logger Logger = LoggerFactory.getLogger(ComputerDAOImpl.class);

    @Autowired 
    private DataSource dataSource;

    @Autowired
    private ComputerDAO computerDAO;

    private void deleteALlCompaniesWithId(long id, Connection conn) throws SQLException, DAOException {
        try (PreparedStatement deleteCompanyStatement = conn.prepareStatement(DELETE_COMPANY);) {
            deleteCompanyStatement.setLong(1, id);
            deleteCompanyStatement.execute();
        } catch (SQLException e) {
            Logger.error("Error while deleting company, rolling back : ", e);
            throw new DAOException("Couldn't delete company");
        }
    }

    @Override
    public void deleteCompany(long id) throws DAOException {
        Logger.info("DAO : Delete Company");

        try (Connection conn = getConnection();) {
            computerDAO.deleteComputers(id, conn);
            deleteALlCompaniesWithId(id, conn);
        } catch (SQLException e) {
            Logger.debug("Couldn't get connection : ", e);
            throw new DAOException("couldn't get connection");
        }
    }

    @Override
    public Optional<Company> getCompany(Company c) throws DAOException {
        return getCompany(c.getId());
    }

    @Override
    public Optional<Company> getCompany(Long id) throws DAOException {
        Logger.info("get company");
        Company c = null;

        try (Connection conn = getConnection(); PreparedStatement st = conn.prepareStatement(SELECT_COMPANY);) {

            if (id != null) {
                st.setLong(1, id);
            } else {
                st.setNull(1, java.sql.Types.BIGINT);
            }

            try (ResultSet rs = st.executeQuery();) {
                if (rs.next()) {
                    c = CompanyMapper.createCompany(rs);
                }
            }

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            throw new DAOException("Couldn't find company with ID : " + id);
        }

        return Optional.ofNullable(c);
    }

    @Override
    public List<Company> getListCompanies() throws DAOException {
        Logger.info("get list companies");
        ArrayList<Company> companies = new ArrayList<>();

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(SELECT_COMPANIES)) {

            while (rs.next()) {
                companies.add(CompanyMapper.createCompany(rs));
            }

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            throw new DAOException("Couldn't fetch companies list");
        }

        return companies;
    }

    @Override
    public List<Company> getListCompanies(final int pageNumber, final int pageSize) throws DAOException {
        Logger.info("get list companies");
        ArrayList<Company> companies = new ArrayList<>();
        try (Connection conn = getConnection();
                PreparedStatement st = conn.prepareStatement(SELECT_COMPANIES_PAGE);) {

            st.setInt(1, pageSize);
            st.setInt(2, pageSize * pageNumber);
            try (ResultSet rs = st.executeQuery();) {
                while (rs.next()) {
                    companies.add(CompanyMapper.createCompany(rs));
                }

                if (companies.isEmpty()) {
                    throw new DAOException("Given page number is greater than page count");
                }
            }

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            throw new DAOException("Couldn't fetch companies list");
        }

        return companies;
    }

    @Override
    public int getListCompaniesPageCount(int pageSize) throws DAOException {
        Logger.info("get page count");
        int pageCount = 0;

        try (Connection conn = getConnection();
                PreparedStatement st = conn.prepareStatement(SELECT_COUNT_COMPANIES);
                ResultSet rs = st.executeQuery()) {

            rs.next();
            int companyCount = rs.getInt(1);
            pageCount = companyCount / pageSize;

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            throw new DAOException("Couldn't get companies page count");
        }

        return pageCount;
    }
    
    private Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection(dataSource);
    }

}
