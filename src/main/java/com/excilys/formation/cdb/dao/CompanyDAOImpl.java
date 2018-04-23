package com.excilys.formation.cdb.dao;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.mapper.RowCompanyMapper;
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

    public CompanyDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void deleteCompany(long id) throws DAOException {
        Logger.info("DAO : Delete Company");
        jdbcTemplate.update(DELETE_COMPUTERS_WITH_COMPANY_ID, new Object[] { id });
        jdbcTemplate.update(DELETE_COMPANY, new Object[] { id });
    }

    @Override
    public Optional<Company> getCompany(Company c) throws DAOException {
        if ((c == null) || (c.getId() == null)) {
            return Optional.empty();
        }
        return getCompany(c.getId());
    }

    @Override
    public Optional<Company> getCompany(Long id) throws DAOException {
        Logger.info("get company");
        try {
            return Optional
                    .of(jdbcTemplate.queryForObject(SELECT_COMPANY, new Object[] { id }, new RowCompanyMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Company> getListCompanies() throws DAOException {
        Logger.info("get list companies");
        return jdbcTemplate.query(SELECT_COMPANIES, new RowCompanyMapper());
    }

    @Override
    public List<Company> getListCompanies(int pageNumber, int pageSize) throws DAOException {
        Logger.info("get list companies");
        return jdbcTemplate.query(SELECT_COMPANIES_PAGE, new Object[] { pageSize, pageSize * pageNumber },
                new RowCompanyMapper());

    }

    @Override
    public int getListCompaniesPageCount(int pageSize) throws DAOException {
        Logger.info("get page count");
        return jdbcTemplate.queryForObject(SELECT_COUNT_COMPANIES, Integer.class) / pageSize;
    }

}