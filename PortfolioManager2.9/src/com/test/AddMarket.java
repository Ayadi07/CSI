package com.test;

import com.PortfolioManager.domain.dao.IMarket;
import com.PortfolioManager.domain.entities.Market;
import com.PortfolioManager.domain.impl.MarketDAO;
import com.PortfolioManager.utilities.technic.JPA;

public class AddMarket {

    /**
     * @param args
     */
    public static void main(String[] args) {
        JPA.setPersistenceUnit("PortM");
        IMarket iMarket = new MarketDAO();
        Market market1 = new Market("^fchi");
        Market market2 = new Market("^ndx");
        market1.setMarketId("France_Paris_CAC40");
        market2.setMarketId("US_Nasdaq_NDX");
        market1.setIndice(0);
        market1.setOldIndice(0);
        
        market2.setOldIndice(0);
        market2.setIndice(0);
        
        iMarket.persist(market2);
        iMarket.persist(market1);
    }
}
