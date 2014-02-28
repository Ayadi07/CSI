package com.PortfolioManager.domain.impl;

import com.PortfolioManager.domain.dao.IInvestorDAO;
import com.PortfolioManager.domain.entities.Investor;
import com.PortfolioManager.utilities.technic.JPA;
import javax.persistence.EntityManager;

public class InvestorDAO implements IInvestorDAO {

    private EntityManager entityManager = JPA.getEntityManager();

    @Override
    public void persist(Investor i) {
 if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        
        try {
            entityManager.persist(i);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            // popup
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void update(Investor i) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(i);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Investor i) {
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(i);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Investor getInvestorById(String l) {
        try {
            Investor i = entityManager.find(Investor.class, l);
            return i;
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            return null;
        }
    }
}
