package com.PortfolioManager.domain.impl;

import com.PortfolioManager.domain.dao.IMarket;
import com.PortfolioManager.domain.entities.Market;
import com.PortfolioManager.utilities.technic.JPA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class MarketDAO implements IMarket {

    private EntityManager entityManager = JPA.getEntityManager();

    @Override
    public void persist(Market m) {

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(m);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            // popup
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void update(Market m) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(m);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(String market) {
        entityManager.getTransaction().begin();
        try {
            Market m = getMarketById(market);
            entityManager.remove(m);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Market getMarketById(String m) {
        try {
            Market i = entityManager.find(Market.class, m);
            return i;
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Market> getListMarket() {
        try {
            String queryString = "SELECT m FROM Market m ";
            Query query = entityManager.createQuery(queryString);
            List<Market> list = query.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
