/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PortfolioManager.domain.impl;


import com.PortfolioManager.domain.dao.IPortfolioHistory;
import com.PortfolioManager.domain.entities.PortfolioHistory;
import com.PortfolioManager.utilities.technic.JPA;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Ahmed
 */
public class PortfolioHistoryDAO implements IPortfolioHistory{

    private EntityManager entityManager = JPA.getEntityManager();
    @Override
    public void persist(PortfolioHistory p) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        try {
            entityManager.persist(p);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void update(PortfolioHistory p) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        try {
            entityManager.merge(p);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public PortfolioHistory getHistoryByYearAndMonth(Long portId) {
        try {
            List<PortfolioHistory> list=getPortfolioHistory(portId);
            Iterator<PortfolioHistory>it=list.iterator();
            while(it.hasNext())
            {
                PortfolioHistory p=it.next();
                if(p.getMonthOfReturn().getMonth()==new Date().getMonth())
                    if(p.getMonthOfReturn().getYear()==new Date().getYear())
                        return p;
            }
            return null;
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PortfolioHistory> getPortfolioHistory(Long portId) {
        
         try {
             Long id=portId;
            String queryString = "SELECT p FROM PortfolioHistory p where p.portfolio.id= :id";
            Query query = entityManager.createQuery(queryString).setParameter("id", id);
            List<PortfolioHistory> list = query.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
