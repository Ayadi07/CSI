/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

import com.PortfolioManager.domain.dao.IPortfolioOperation;
import com.PortfolioManager.domain.entities.PortfolioOperation;
import com.PortfolioManager.domain.impl.PortfolioOperationDAO;
import com.PortfolioManager.utilities.technic.JPA;
import java.util.List;

/**
 *
 * @author Ahmed
 */
public class testOperations {
    public static void main(String[] args) {
        JPA.setPersistenceUnit("PortM");
        IPortfolioOperation iPortfolioOperation =new PortfolioOperationDAO();
        
        List<PortfolioOperation>list=iPortfolioOperation.getMonthlyOperations(2l, "2013","04");
        System.out.println(list.size()+"2013"+"04");
    }

}
