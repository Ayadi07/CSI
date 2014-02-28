/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

import com.PortfolioManager.utilities.technic.JPA;
import com.PortfolioManager.businessGUI.chartTools.MarketStocks;
import java.util.Vector;

/**
 *
 * @author Ahmed
 */
public class testStocksFile {
    public static void main(String[] args) {
        
        Vector<String>v=MarketStocks.getMarketStoks("France_Paris_CAC40");
        System.out.println(v);
    }

}
