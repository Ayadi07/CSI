/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.portfolioManagement.performance.data;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

/**
 *
 * @author Ahmed
 */
public class MarketDataUtilities {

    private static Vector<Double> typicalValues;
    private static Vector<Double> yields;
    public static MarketDataUtilities marketIndexQuotes;

    private MarketDataUtilities() {
        typicalValues = new Vector<Double>();
        yields = new Vector<Double>();
    }

    public static MarketDataUtilities getMe() {
        if (marketIndexQuotes == null) {
            marketIndexQuotes = new MarketDataUtilities();
        }
        return marketIndexQuotes;
    }

    /**
     * @return the typicalValues
     */
    public Vector<Double> getTypicalValues() {
        return typicalValues;
    }

    /**
     * @param typicalValues the typicalValues to set
     */
    public void setTypicalValues(Vector<Double> typicalValues) {
        this.typicalValues = typicalValues;
    }

    /**
     * @return the yields
     */
    public Vector<Double> getYields() {
        return yields;
    }

    /**
     * @param yields the yields to set
     */
    public void setYields() {
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

    public static double calculateVariance() {
        Variance variance = new Variance();
        double[] y = new double[yields.size()];
        //StandardDeviatiom.evaluate(Double[] values)!!!!
        int i = 0;
        Iterator<Double> it = yields.iterator();
        while (it.hasNext()) {
            y[i] = it.next();
            i++;
        }
        //StandardDeviatiom.evaluate(Double[] values)!!!!
        refresh();
        return variance.evaluate(y);
    }
    public static void refresh()
    {
        yields=new Vector<Double>();
        typicalValues=new Vector<Double>();
    }
}
