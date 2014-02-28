/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

import com.PortfolioManager.domain.dao.IMarket;
import com.PortfolioManager.domain.entities.Market;
import com.PortfolioManager.domain.impl.MarketDAO;
import com.PortfolioManager.utilities.technic.JPA;

/**
 *
 * @author Ahmed
 */
public class TestMarketVariance {

    public static void main(String[] args) {
        JPA.setPersistenceUnit("PortM");
        IMarket im=new MarketDAO();
        Market market=im.getMarketById("France_Paris_CAC40");
        market.calculateGetMonthlyVariance();
        market.getMonthlyVariance();
        
    }
}
