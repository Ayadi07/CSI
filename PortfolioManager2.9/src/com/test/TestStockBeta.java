/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

import com.PortfolioManager.domain.dao.IStockDAO;
import com.PortfolioManager.domain.entities.Stock;
import com.PortfolioManager.domain.impl.StockDAO;
import com.PortfolioManager.utilities.technic.JPA;
import org.eclipse.persistence.internal.helper.JPAClassLoaderHolder;

/**
 *
 * @author Ahmed
 */
public class TestStockBeta {

    public static void main(String[] args) {
        JPA.setPersistenceUnit("PortM");
        IStockDAO isdao=new StockDAO();
        
        Stock stock=isdao.getStockById(1);
        
        
        
        stock.calculateMonthlyBeta("France_Paris_CAC40");
        System.out.println(stock.getBeta());
        
    }
}