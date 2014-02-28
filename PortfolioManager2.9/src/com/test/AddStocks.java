/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import com.PortfolioManager.domain.dao.IOrderStock;
import com.PortfolioManager.domain.dao.IStockDAO;
import com.PortfolioManager.domain.entities.OrderStock;
import com.PortfolioManager.domain.entities.Stock;
import com.PortfolioManager.domain.impl.OrderStockDAO;
import com.PortfolioManager.domain.impl.StockDAO;
import com.PortfolioManager.utilities.technic.JPA;
import java.util.Date;

/**
 *
 * @author Ahmed
 */
public class AddStocks {

    public static void main(String[] args) {
        JPA.setPersistenceUnit("PortM");
        IStockDAO iStockDAO=new StockDAO();
        Stock stock=new Stock();
        stock.setBuyPrice(50.93);
        stock.setCurrentPrice(50.21);
        stock.setBeta(0.265370109861589);
        stock.setBetaLatest(new Date());
        stock.setName("Virgin Media, Inc.");
        stock.setSymbol("VMED");
        
        IOrderStock ios=new OrderStockDAO();
        OrderStock orderStock=ios.getOrderById(304l);
        orderStock.setStock(stock);
        stock.setOrderStock(orderStock);
        ios.update(orderStock);
        
        
        
    }
    
}
