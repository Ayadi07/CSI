/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.domain.impl;

import com.PortfolioManager.domain.dao.IPortfolioOperation;
import com.PortfolioManager.domain.entities.PortfolioOperation;
import com.PortfolioManager.domain.entities.OrderStock;
import com.PortfolioManager.utilities.technic.JPA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Ahmed
 */
public class PortfolioOperationDAO implements IPortfolioOperation {

    private EntityManager entityManager = JPA.getEntityManager();

    @Override
    public void persist(PortfolioOperation o) {
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
    public List<PortfolioOperation> getOperationtByPortfolio(Long portId) {
        try {
            String queryString = "SELECT o FROM Operation o where o.portfolio.id=" + portId;
            Query query = entityManager.createQuery(queryString);
            List<PortfolioOperation> list = query.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PortfolioOperation> getMonthlyOperations(Long portId, String year, String month) {
        try {
            String queryString = "SELECT p FROM PortfolioOperation p where Func('date_format',p.operationDate,'%Y%m')=:yearMonth";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("yearMonth", year+month);
            List<PortfolioOperation> list = query.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
