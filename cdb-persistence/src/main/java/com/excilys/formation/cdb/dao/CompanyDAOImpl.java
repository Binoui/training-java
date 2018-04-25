package com.excilys.formation.cdb.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.mapper.RowCompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company_;

@Repository("CompanyDAO")
public class CompanyDAOImpl implements CompanyDAO {

    private static final String SELECT_COMPANIES = "select ca_id, ca_name from company;";
    private static final String SELECT_COMPANIES_PAGE = "select ca_id, ca_name from company order by ca_id limit ? offset ? ;";
    private static final String SELECT_COUNT_COMPANIES = "select count(ca_id) from company;";
    private static final String DELETE_COMPANY = "delete from company where ca_id = ?";
    private static final String DELETE_COMPUTERS_WITH_COMPANY_ID = "delete from computer where ca_id = ?;";

    private static final Logger Logger = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private JdbcTemplate jdbcTemplate;

    private EntityManagerFactory entityManagerFactory;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

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
    public Optional<Company> getCompany(Long id) {
        Logger.info("get company");
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Company> query = builder.createQuery(Company.class);
        Root<Company> companyRoot = query.from(Company.class);
        Path<Long> idPath = companyRoot.get(Company_.ca_id);
        Path<String> namePath = companyRoot.get(Company_.ca_name);
        query.multiselect(idPath, namePath);
        query.where(builder.equal(idPath, id));
        return Optional.ofNullable(entityManagerFactory.createEntityManager().createQuery(query).getSingleResult());
    }

    @Override
    public List<Company> getListCompanies() throws DAOException {
        Logger.info("get list companies");
        CriteriaQuery<Company> query = entityManagerFactory.getCriteriaBuilder().createQuery(Company.class);
        query.from(Company.class);
        return entityManagerFactory.createEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Company> getListCompanies(int pageNumber, int pageSize) throws DAOException {
        Logger.info("get list companies");
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> from = criteriaQuery.from(Company.class);
        CriteriaQuery<Company> select = criteriaQuery.select(from);
        TypedQuery<Company> typedQuery = entityManagerFactory.createEntityManager().createQuery(select);
        typedQuery.setFirstResult(pageSize * pageNumber);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    @Override
    public int getListCompaniesPageCount(int pageSize) throws DAOException {
        Logger.info("get page count");
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Integer> countQuery = criteriaBuilder.createQuery(Integer.class);
        countQuery.multiselect(criteriaBuilder.count(countQuery.from(Company.class)));
        return entityManagerFactory.createEntityManager().createQuery(countQuery).getSingleResult();
    }

}