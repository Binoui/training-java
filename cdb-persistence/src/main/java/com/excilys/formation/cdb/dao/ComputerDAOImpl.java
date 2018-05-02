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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company_;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer_;

@Repository("ComputerDAO")
public class ComputerDAOImpl implements ComputerDAO {

    private static final Logger Logger = LoggerFactory.getLogger(ComputerDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    @Override
    public Long createComputer(Computer computer) {
        Logger.info("create computer  :  " + computer);
        computer.setId(null);
        entityManager.persist(computer);
        entityManager.flush();
        return computer.getId();
    }

    @Override
    public void deleteComputer(Computer computer) {
        Logger.info("delete computer");
        CriteriaDelete<Computer> deleteQuery = criteriaBuilder.createCriteriaDelete(Computer.class);
        Root<Computer> rootComputer = deleteQuery.from(Computer.class);
        deleteQuery.where(criteriaBuilder.equal(rootComputer.get(Computer_.id), computer.getId()));
        entityManager.createQuery(deleteQuery).executeUpdate();
    }

    @Override
    public void deleteComputers(List<Long> idsToDelete) {
        Logger.info("delete computers");

        CriteriaDelete<Computer> deleteQuery = criteriaBuilder.createCriteriaDelete(Computer.class);
        Root<Computer> rootComputer = deleteQuery.from(Computer.class);
        deleteQuery.where(rootComputer.get(Computer_.id).in(idsToDelete));
        entityManager.createQuery(deleteQuery).executeUpdate();
    }

    @Override
    public Optional<Computer> getComputer(Computer computer) {
        if ((computer == null) || (computer.getId() == null)) {
            return Optional.empty();
        }
        return getComputer(computer.getId());
    }

    @Override
    public Optional<Computer> getComputer(long id) {
        Logger.debug("get computer");
        CriteriaQuery<Computer> query = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> companyRoot = query.from(Computer.class);
        Path<Long> idPath = companyRoot.get(Computer_.id);
        query.where(criteriaBuilder.equal(idPath, id));
        return entityManager.createQuery(query).getResultList().stream().findFirst();
    }

    @Override
    public int getComputerCount() {
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        query.select(criteriaBuilder.count(query.from(Computer.class)));
        return (int) Math.ceil(entityManager.createQuery(query).getSingleResult());
    }

    @Override
    public int getComputerCount(String searchWord) {
        Logger.info("getComputerCount with searchWord : " + searchWord);
        CriteriaQuery<Long> queryLong = criteriaBuilder.createQuery(Long.class);
        Root<Computer> computerRoot = queryLong.from(Computer.class);
        Join<Computer, Company> join = computerRoot.join(Computer_.company, JoinType.LEFT);
        queryLong.select(criteriaBuilder.count(computerRoot));
        putLikeOnQuery(searchWord, queryLong, computerRoot, join);
        return entityManager.createQuery(queryLong).getSingleResult().intValue();
    }

    @Override
    public List<Computer> getListComputers() {
        Logger.info("list computers no args");
        CriteriaQuery<Computer> query = criteriaBuilder.createQuery(Computer.class);
        query.from(Computer.class);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending) {
        Logger.info("list computers with pages / orderBy");
        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> root = criteriaQuery.from(Computer.class);
        putOrderByOnQuery(column, ascending, criteriaQuery, root);
        return getPageFromQuery(pageNumber, pageSize, criteriaQuery);
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending, String searchWord) {
        Logger.info("list computers with pages / orderBy / search");
        if (StringUtils.isBlank(searchWord)) {
            Logger.info("search word was null, using regular list computer");
            return getListComputers(pageNumber, pageSize, column, ascending);
        }

        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> root = criteriaQuery.from(Computer.class);
        Join<Computer, Company> join = root.join(Computer_.company, JoinType.LEFT);
        putOrderByOnQuery(column, ascending, criteriaQuery, root);
        putLikeOnQuery(searchWord, criteriaQuery, root, join);
        return getPageFromQuery(pageNumber, pageSize, criteriaQuery);
    }

    @Override
    public int getListComputersPageCount(int pageSize) {
        return (int) Math.ceil(getComputerCount() / (double) pageSize);
    }

    @Override
    public int getListComputersPageCount(int pageSize, String searchWord) {
        int pageCount = 0;

        int computerCount = getComputerCount(searchWord);
        pageCount = ((computerCount + pageSize) - 1) / pageSize;

        return pageCount;
    }

    private List<Computer> getPageFromQuery(int pageNumber, int pageSize, CriteriaQuery<Computer> criteriaQuery) {
        TypedQuery<Computer> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageSize * pageNumber);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    private void putLikeOnQuery(String searchWord, CriteriaQuery<?> criteriaQuery, Root<Computer> root,
            Join<Computer, Company> join) {
        searchWord = '%' + searchWord + '%';
        Path<String> computerNamePath = root.get(Computer_.name);
        Path<String> companyNamePath = join.get(Company_.ca_name);
        Predicate likeRestriction = criteriaBuilder.or(criteriaBuilder.like(computerNamePath, searchWord),
                criteriaBuilder.like(companyNamePath, searchWord));
        criteriaQuery.where(likeRestriction);
    }

    private void putOrderByOnQuery(SortableComputerColumn column, boolean ascending,
            CriteriaQuery<Computer> criteriaQuery, Root<Computer> root) {
        Order orderBy;
        if (ascending) {
            orderBy = criteriaBuilder.asc(root.get(column.getColumn()));
        } else {
            orderBy = criteriaBuilder.desc(root.get(column.getColumn()));
        }

        criteriaQuery.orderBy(orderBy);
    }

    @Override
    public void updateComputer(Computer computer) {
        Logger.info("update computer");
        entityManager.merge(computer);
    }

}
