package com.PortfolioManager.domain.impl;

import com.PortfolioManager.domain.dao.IStockDAO;
import com.PortfolioManager.domain.entities.Stock;
import com.PortfolioManager.utilities.technic.JPA;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class StockDAO implements IStockDAO {

    private EntityManager entityManager = JPA.getEntityManager();

    @Override
    public void persist(Stock s) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(s);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            // popup
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void update(Stock s) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(s);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Stock s) {
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(s);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }

    }

    @Override
    public Stock getStockById(long stockId) {
        Stock s=null;
        try {
            s = entityManager.find(Stock.class, stockId);
            return s;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<Stock> getStocks(String sym) {
        try {
            List<Stock> list;
            String q = "SELECT s FROM Stock s WHERE s.symbol= :sym";
            Query query = entityManager.createQuery(q).setParameter("sym", sym);
            list = query.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }
}
