package com.PortfolioManager.domain.impl;

import com.PortfolioManager.domain.dao.IAccountDAO;
import com.PortfolioManager.domain.entities.Account;
import com.PortfolioManager.utilities.technic.JPA;
import javax.persistence.EntityManager;

public class AccountDAO implements IAccountDAO {

    private EntityManager entityManager = JPA.getEntityManager();

    @Override
    public void persist(Account a) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(a);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            // popup
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void update(Account a) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(a);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }

    }

    @Override
    public void delete(Account a) {
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(a);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Account getAccountById(Long accountId) {
        try {
            Account a = entityManager.find(Account.class, accountId);
            return a;
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            return null;
        }
    }
}
