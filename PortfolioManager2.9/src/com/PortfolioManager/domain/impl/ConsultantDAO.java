package com.PortfolioManager.domain.impl;

import com.PortfolioManager.domain.dao.IConsultant;
import com.PortfolioManager.domain.entities.Consultant;
import com.PortfolioManager.utilities.technic.JPA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;


public class ConsultantDAO implements IConsultant {

    EntityManager entityManager = JPA.getEntityManager();

    @Override
    public void persist(Consultant c) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(c);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void update(Consultant c) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(c);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Consultant c) {
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(c);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // popup
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }

    }

    @Override
    public Consultant getConsultanttById(String ConsultantId) {
        try {
            Consultant c = entityManager.find(Consultant.class, ConsultantId);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Consultant> getAllConsultant() {
        String queryString = "SELECT c FROM Consultant c";
        Query query = entityManager.createQuery(queryString);
        List<Consultant> list = query.getResultList();
        return list;
    }
}
