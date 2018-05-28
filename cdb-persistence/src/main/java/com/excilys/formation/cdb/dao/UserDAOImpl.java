package com.excilys.formation.cdb.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.Role;
import com.excilys.formation.cdb.model.User;
import com.excilys.formation.cdb.model.User_;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    @Override
    public void addUser(User user) throws NameAlreadyPresentException {
        try {
            entityManager.persist(user);
        } catch (Exception e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw new NameAlreadyPresentException(); 
            }
        }
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> companyRoot = query.from(User.class);
        Path<String> usernamePath = companyRoot.get(User_.username);
        query.where(criteriaBuilder.equal(usernamePath, username));
        return entityManager.createQuery(query).getResultList().stream().findFirst();
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

}
