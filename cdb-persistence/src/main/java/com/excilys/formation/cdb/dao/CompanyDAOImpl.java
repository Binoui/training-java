package com.excilys.formation.cdb.dao;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company_;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer_;

@Repository("CompanyDAO")
public class CompanyDAOImpl implements CompanyDAO {

    private static final Logger Logger = LoggerFactory.getLogger(ComputerDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    @Override
    public void createCompany(Company c) {
        c.setId(null);
        entityManager.persist(c);
        entityManager.flush();
    }

    @Override
    public void deleteCompany(Long id) {
        Logger.info("DAO : Delete Company");

        CriteriaDelete<Computer> deleteComputerQuery = criteriaBuilder.createCriteriaDelete(Computer.class);
        Root<Computer> rootComputer = deleteComputerQuery.from(Computer.class);
        deleteComputerQuery.where(criteriaBuilder.equal(rootComputer.get(Computer_.company), id));
        entityManager.createQuery(deleteComputerQuery).executeUpdate();

        CriteriaDelete<Company> deleteCompanyQuery = criteriaBuilder.createCriteriaDelete(Company.class);
        Root<Company> rootCompany = deleteCompanyQuery.from(Company.class);
        deleteCompanyQuery.where(criteriaBuilder.equal(rootCompany.get(Company_.ca_id), id));
        entityManager.createQuery(deleteCompanyQuery).executeUpdate();

    }

    @Override
    public Optional<Company> getCompany(Company c) {
        if ((c == null) || (c.getId() == null)) {
            return Optional.empty();
        }
        return getCompany(c.getId());
    }

    @Override
    public Optional<Company> getCompany(Long id) {
        Logger.info("get company");
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = builder.createQuery(Company.class);
        Root<Company> companyRoot = query.from(Company.class);
        Path<Long> idPath = companyRoot.get(Company_.ca_id);
        query.where(builder.equal(idPath, id));
        return entityManager.createQuery(query).getResultList().stream().findFirst();
    }

    @Override
    public List<Computer> getCompanyComputers(int idCompany) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Computer> query = qb.createQuery(Computer.class);
        Root<Computer> rootComputer = query.from(Computer.class);
        query.where(criteriaBuilder.equal(rootComputer.get(Computer_.company), idCompany));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Company> getListCompanies() {
        Logger.info("get list companies");
        CriteriaQuery<Company> query = entityManager.getCriteriaBuilder().createQuery(Company.class);
        query.from(Company.class);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Company> getListCompanies(int pageNumber, int pageSize) {
        Logger.info("get list companies");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> from = criteriaQuery.from(Company.class);
        CriteriaQuery<Company> select = criteriaQuery.select(from);
        return getPageFromQuery(pageNumber, pageSize, select);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Company> getListCompanies(int pageNumber, int pageSize, SortableCompanyColumn column, boolean ascending,
            String searchWord) {
        Logger.info("list companies with pages / search");
        if (StringUtils.isBlank(searchWord)) {
            Logger.info("search word was null, using regular list companies");
            return getListCompanies(pageNumber, pageSize);
        }

        searchWord = '%' + searchWord + '%';

        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> root = criteriaQuery.from(Company.class);
        Path<String> companyNamePath = root.get(Company_.ca_name);
        criteriaQuery.where(criteriaBuilder.like(companyNamePath, searchWord));

        Order orderBy = getOrderByFromAscending(column, ascending, root);
        criteriaQuery.orderBy(orderBy);

        return getPageFromQuery(pageNumber, pageSize, criteriaQuery);
    }

    @Override
    public int getListCompaniesPageCount(int pageSize) {
        Logger.info("get page count");
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Company.class)));
        return (int) Math.ceil(entityManager.createQuery(cq).getSingleResult() / (double) pageSize);
    }

    private Order getOrderByFromAscending(SortableCompanyColumn column, boolean ascending, Root<Company> root) {
        Order orderBy;
        if (ascending) {
            orderBy = criteriaBuilder.asc(root.get(column.getColumn()));
        } else {
            orderBy = criteriaBuilder.desc(root.get(column.getColumn()));
        }
        return orderBy;
    }

    private List<Company> getPageFromQuery(int pageNumber, int pageSize, CriteriaQuery<Company> criteriaQuery) {
        TypedQuery<Company> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageSize * pageNumber);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public void updateCompany(Company company) {
        Logger.info("update company");
        entityManager.merge(company);
    }
}