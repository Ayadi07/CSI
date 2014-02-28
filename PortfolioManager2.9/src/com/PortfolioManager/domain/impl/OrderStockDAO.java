package com.PortfolioManager.domain.impl;

import com.PortfolioManager.domain.dao.IOrderStock;
import com.PortfolioManager.domain.entities.OrderStock;
import com.PortfolioManager.utilities.technic.JPA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class OrderStockDAO implements IOrderStock {

    private EntityManager entityManager = JPA.getEntityManager();

    @Override
    public void persist(OrderStock o) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        try {
            entityManager.persist(o);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            // popup
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void update(OrderStock o) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        try {
            entityManager.merge(o);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(long orderId) {
//        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
//        }
        OrderStock o = getOrderById(orderId);
        try {
            entityManager.remove(o);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public OrderStock getOrderById(long orderId) {
        try {
            OrderStock o = entityManager.find(OrderStock.class, orderId);
            return o;
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderStock> listOrderByPortfolio(Long portfolioId) {

        try {
            String queryString = "SELECT o FROM OrderStock o where o.portfolio.id="
                    + portfolioId;
            Query query = entityManager.createQuery(queryString);
            List<OrderStock> list = query.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}