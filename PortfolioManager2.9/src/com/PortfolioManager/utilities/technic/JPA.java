/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.utilities.technic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ahmed
 */
public class JPA {

    private static EntityManagerFactory entityManagerFactory; // qui va generer
    // l'entity
    // manager
    private static EntityManager entityManager; // gerer l'unite de persistance

    public static void setPersistenceUnit(String pu) // to set Persistance Unit
    {
        setEntityManagerFactory(Persistence.createEntityManagerFactory(pu));
        entityManager = entityManagerFactory.createEntityManager();// gerer tout
        // ce qui
        // est acces
        // a la base
        // de
        // donnees
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void setEntityManagerFactory(
            EntityManagerFactory entityManagerFactory) {
        JPA.entityManagerFactory = entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void setEntityManager(EntityManager entityManager) {
        JPA.entityManager = entityManager;
    }
}
