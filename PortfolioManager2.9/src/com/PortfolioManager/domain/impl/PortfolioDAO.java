package com.PortfolioManager.domain.impl;

import com.PortfolioManager.domain.dao.IPortfolioDAO;
import com.PortfolioManager.domain.entities.Market;
import com.PortfolioManager.domain.entities.Portfolio;
import com.PortfolioManager.utilities.technic.JPA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class PortfolioDAO implements IPortfolioDAO {

    private EntityManager entityManager = JPA.getEntityManager();

    @Override
    public void persist(Portfolio p) {

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(p);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            // popup
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void update(Portfolio p) {
         if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        try {
            entityManager.merge(p);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Portfolio p) {
         if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        try {
            entityManager.remove(p);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Portfolio getPortfolioById(Long portfolioId) {
        try {
            Portfolio p = entityManager.find(Portfolio.class, portfolioId);
            return p;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Portfolio getPortfolioByName(String name, Long id) {
        try {
            String queryString = "SELECT p FROM Portfolio p where p.account.id=" + id + " and p.name='" + name + "'";
            Query query = entityManager.createQuery(queryString);
            return (Portfolio) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Portfolio> getPortfoliByAccount(Long accountId) {

        try {
            String queryString = "SELECT p FROM Portfolio p where p.account.id=" + accountId;
            Query query = entityManager.createQuery(queryString);

            List<Portfolio> list = query.getResultList();
            return list;
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            return null;
        }
    }
}
