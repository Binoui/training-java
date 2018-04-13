package com.excilys.formation.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;

@Repository("CompanyDAO")
public class CompanyDAOImpl implements CompanyDAO {

    private static final String SELECT_COMPANIES = "select ca_id, ca_name from company;";
    private static final String SELECT_COMPANIES_PAGE = "select ca_id, ca_name from company order by ca_id limit ? offset ? ;";
    private static final String SELECT_COUNT_COMPANIES = "select count(ca_id) from company;";
    private static final String SELECT_COMPANY = "select ca_id, ca_name from company where ca_id = ?";
    private static final String DELETE_COMPANY = "delete from company where ca_id = ?";
    private static final String DELETE_COMPUTERS_WITH_COMPANY_ID = "delete from computer where ca_id = ?;";

    private static final Logger Logger = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void deleteCompany(long id) throws DAOException {
        Logger.info("DAO : Delete Company");

        Connection conn;
        conn = getConnection();
        try (PreparedStatement deleteComputersStatement = conn.prepareStatement(DELETE_COMPUTERS_WITH_COMPANY_ID);) {
            deleteComputersStatement.setLong(1, id);
            deleteComputersStatement.execute();
        } catch (SQLException e) {
            Logger.error("Error while deleting computers, rolling back : ", e);
            throw new DAOException("Couldn't delete computers");
        }

        try (PreparedStatement deleteCompanyStatement = conn.prepareStatement(DELETE_COMPANY);) {
            deleteCompanyStatement.setLong(1, id);
            deleteCompanyStatement.execute();
        } catch (SQLException e) {
            Logger.error("Error while deleting company, rolling back : ", e);
            throw new DAOException("Couldn't delete company");
        }
    }

    @Override
    public Optional<Company> getCompany(Company c) throws DAOException {
        return getCompany(c.getId());
    }

    @Override
    public Optional<Company> getCompany(Long id) throws DAOException {
        Logger.info("get company");
        try {
            return Optional
                    .of(jdbcTemplate.queryForObject(SELECT_COMPANY, new Object[] { id }, (ResultSet rs, int arg1) -> {
                        return CompanyMapper.createCompany(rs);
                    }));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Company> getListCompanies() throws DAOException {
        Logger.info("get list companies");
        return jdbcTemplate.query(SELECT_COMPANIES, (ResultSet rs, int arg1) -> {
            return CompanyMapper.createCompany(rs);
        });
    }

    @Override
    public List<Company> getListCompanies(int pageNumber, int pageSize) throws DAOException {
        Logger.info("get list companies");
        return jdbcTemplate.query(SELECT_COMPANIES_PAGE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement st) throws SQLException {
                st.setInt(1, pageSize);
                st.setInt(2, pageSize * pageNumber);
            }
        }, (ResultSet rs, int arg1) -> {
            return CompanyMapper.createCompany(rs);
        });
    }

    @Override
    public int getListCompaniesPageCount(int pageSize) throws DAOException {
        Logger.info("get page count");
        return jdbcTemplate.queryForObject(SELECT_COUNT_COMPANIES, Integer.class) / pageSize;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
    }
}