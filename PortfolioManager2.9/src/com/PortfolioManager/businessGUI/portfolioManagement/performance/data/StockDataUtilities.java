/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.portfolioManagement.performance.data;

import com.PortfolioManager.businessGUI.portfolioManagement.performance.data.MarketDataUtilities;
import com.PortfolioManager.businessGUI.chartTools.StockChart;
import com.PortfolioManager.domain.dao.IMarket;
import com.PortfolioManager.domain.entities.Market;
import com.PortfolioManager.domain.impl.MarketDAO;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import org.apache.commons.math3.stat.correlation.Covariance;

/**
 *
 * @author Ahmed
 */
public class StockDataUtilities {

    private static Vector<Double> typicalValues;
    private static Vector<Double> yields;
    public static StockDataUtilities stockDataUtilities;
    private static Vector<Double> marketYields;

    public StockDataUtilities() {
        typicalValues = new Vector<Double>();
        yields = new Vector<Double>();
        marketYields = new Vector<Double>();
    }

    public static StockDataUtilities getMe() {
        if (stockDataUtilities == null) {
            stockDataUtilities = new StockDataUtilities();
        }
        return stockDataUtilities;
    }

    /**
     * @return the typicalValues
     */
    public static Vector<Double> getTypicalValues() {
        return typicalValues;
    }

    /**
     * @param aTypicalValues the typicalValues to set
     */
    public static void setTypicalValues(Vector<Double> aTypicalValues) {
        typicalValues = aTypicalValues;
    }

    /**
     * @return the yields
     */
    public static Vector<Double> getYields() {
        return yields;
    }

    /**
     * @param aYields the yields to set
     */
    public static void setYields() {
        Iterator<Double> iterator = typicalValues.iterator();
        Collections.reverse(typicalValues);
        double older;
        double newer;
        while (iterator.hasNext()) {
            newer = iterator.next();
            if (iterator.hasNext()) {
                older = iterator.next();
                yields.add(((newer / older) - 1));
            }
        }
    }

    public static double calculateBeta(String marketSymbol) {
        double beta = 0;
        IMarket iMarket = new MarketDAO();
        Market marketIndex = iMarket.getMarketById(marketSymbol);
        
        marketIndex.dateManagerForStockChart();
        StockChart.getMe().setXyDataSetFromYahoo();
        MarketDataUtilities.getMe().setTypicalValues(StockChart.getMe().getTypicalPrice());
        MarketDataUtilities.getMe().setYields();
        marketYields = MarketDataUtilities.getMe().getYields();
        
        Covariance covariance = new Covariance();
       
        int stock = yields.size();
        int market = marketYields.size();
        int min = stock;
        if (market < stock) {
            min = market;
        }
        double[] cov1 = new double[min];
        double[] cov2 = new double[min];

        int i = 0;
        Iterator<Double> itstock = yields.iterator();
        Iterator<Double> itmarket = marketYields.iterator();
        while (itstock.hasNext() && i<min) {
            cov1[i] = itstock.next();
            i++;
        }
        i = 0;
        while (itmarket.hasNext()&& i<min) {
            cov2[i] = itmarket.next();
            i++;
            System.out.println(i);
        }
        marketIndex.setMonthlyVariance(MarketDataUtilities.getMe().calculateVariance());
        
        beta = covariance.covariance(cov1, cov2);
        beta /= marketIndex.getMonthlyVariance();
        iMarket.update(marketIndex);
        refresh();
        return beta;
    }

    public static void refresh() {
        yields = new Vector<Double>();
        typicalValues = new Vector<Double>();
    }
}
