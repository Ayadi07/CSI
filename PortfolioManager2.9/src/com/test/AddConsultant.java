package com.test;

import com.PortfolioManager.domain.dao.IConsultant;
import com.PortfolioManager.domain.entities.Consultant;
import com.PortfolioManager.domain.impl.ConsultantDAO;
import com.PortfolioManager.utilities.technic.JPA;

public class AddConsultant {

    public static void main(String[] args) {
        JPA.setPersistenceUnit("PortM");
        Consultant consultant = new Consultant("ahmed.ayadi@esprit.tn");
        Consultant consultant2 = new Consultant("amine.zaafouri@esprit.tn");
        consultant.setName("Ayadi Ahmed");
        consultant2.setName("Zaafouri Amine");
        IConsultant iConsultant = new ConsultantDAO();
        iConsultant.persist(consultant2);
        iConsultant.persist(consultant);
    }
}
