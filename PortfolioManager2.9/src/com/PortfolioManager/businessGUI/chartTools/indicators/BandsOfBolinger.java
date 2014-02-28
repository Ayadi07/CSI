/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.chartTools.indicators;

import com.PortfolioManager.businessGUI.chartTools.StockChart;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Ahmed
 */
public class BandsOfBolinger {

    private static Vector<Double> stdeviations = new Vector<Double>();
    private static XYDataset middleBand;
    private static XYDataset UpperBand;
    private static XYDataset LowerBand;
    private static List<OHLCDataItem> dataItemsmiddleBand;
    private static List<OHLCDataItem> dataItemsupperBand;
    private static List<OHLCDataItem> dataItemslowerBand;
    private static boolean selected = false;
    private static int N = 20;

    public static void changeState() {
        selected = !selected;
    }

    public static boolean isSelected() {
        return selected;
    }

    public static void setIndicator(boolean b) {

        final long ONE_DAY = 24 * 60 * 60 * 1000;
        if (b) {

            calculateStandarDeviation(N);
            setBands();
            XYLineAndShapeRenderer maRenderer1 = new XYLineAndShapeRenderer(
                    true, false);
            maRenderer1.setSeriesPaint(0, Color.cyan);

            XYDataset midBand = MovingAverage
                    .createMovingAverage(middleBand, "Simple Moving Average " + N,
                    N * ONE_DAY, 0);

            StockChart.getMainPlot().setRenderer(1, maRenderer1);
            StockChart.getMainPlot().setDataset(1, midBand);



            XYLineAndShapeRenderer maRenderer2 = new XYLineAndShapeRenderer(
                    true, false);
            maRenderer2.setSeriesPaint(0, Color.red);

            XYDataset upBand = MovingAverage
                    .createMovingAverage(UpperBand, "Upper Band",
                    N * ONE_DAY, 0);

            StockChart.getMainPlot().setRenderer(2, maRenderer2);
            StockChart.getMainPlot().setDataset(2, upBand);

            XYLineAndShapeRenderer maRenderer3 = new XYLineAndShapeRenderer(
                    true, false);
            maRenderer3.setSeriesPaint(0, Color.red);

            XYDataset lowBand = MovingAverage
                    .createMovingAverage(LowerBand, "Lower Band",
                    N * ONE_DAY, 0);

            StockChart.getMainPlot().setRenderer(3, maRenderer3);
            StockChart.getMainPlot().setDataset(3, lowBand);

        } else {
            StockChart.getMainPlot().setRenderer(1, null);
            StockChart.getMainPlot().setRenderer(2, null);
            StockChart.getMainPlot().setRenderer(3, null);

            StockChart.getMainPlot().setDataset(1, null);
            StockChart.getMainPlot().setDataset(2, null);
            StockChart.getMainPlot().setDataset(3, null);
        }
    }

    public static double[] extract(double[] pp, int index, int n) {
        double p[] = new double[N];
        for (int i = index, k = 0; i < n + index; i++) {
            p[k] = pp[i];
            k++;
        }
        return p;
    }

    private static void calculateStandarDeviation(int n) {
        Vector<Double> tp = StockChart.getTypicalPrice();

        Iterator<Double> it = tp.iterator();

        StandardDeviation stdDeviation = new StandardDeviation();

        //StandardDeviatiom.evaluate(Double[] values)!!!!
        double portion[] = new double[tp.size()];
        int i = 0;
        while (it.hasNext()) {
            portion[i] = it.next();
            i++;
        }

        //calculating standard deviation begin
        for (int j = 0; j < n - 1; j++) {
            stdeviations.add(0.0);
        }
        for (int j = 0; j <= portion.length - n; j++) {
            stdeviations.add(stdDeviation.evaluate(extract(portion, j, n)));
        }
        //calculating standard deviation end
    }

    /**
     * @return the middleBand
     */
    public static XYDataset getmiddleBand() {
        return middleBand;
    }

    public static void setBands() {
        Iterator<Double> it = StockChart.getTypicalPrice().iterator();
        Iterator<Date> it1 = StockChart.getTypicalPriceDate().iterator();
        Iterator<Double> it2 = StockChart.getTypicalPriceVolume().iterator();
        Iterator<Double> it3 = stdeviations.iterator();
        dataItemslowerBand = new ArrayList<OHLCDataItem>();
        dataItemsmiddleBand = new ArrayList<OHLCDataItem>();
        dataItemsupperBand = new ArrayList<OHLCDataItem>();

        int i = 1;
        while (it.hasNext()) {

            Date date = it1.next();
            double typicalPrice = it.next();
            double stdv = it3.next();
            double volume = it2.next();
            if (i >= N) {
                OHLCDataItem item = new OHLCDataItem(date, typicalPrice, typicalPrice, typicalPrice, typicalPrice, volume);
                dataItemsmiddleBand.add(item);

                item = new OHLCDataItem(date, typicalPrice + 2 * stdv, typicalPrice + 2 * stdv, typicalPrice + 2 * stdv, typicalPrice + 2 * stdv, volume);
                dataItemsupperBand.add(item);
                item = new OHLCDataItem(date, typicalPrice - 2 * stdv, typicalPrice - 2 * stdv, typicalPrice - 2 * stdv, typicalPrice - 2 * stdv, volume);
                dataItemslowerBand.add(item);
            }
            i++;
        }
        // Yahoo provides datum from new to old


        OHLCDataItem[] data = dataItemsmiddleBand.toArray(new OHLCDataItem[dataItemsmiddleBand
                .size()]);
        middleBand = new DefaultOHLCDataset(StockChart.getStockSymbol(), data);

        data = dataItemslowerBand.toArray(new OHLCDataItem[dataItemslowerBand
                .size()]);
        LowerBand = new DefaultOHLCDataset(StockChart.getStockSymbol(), data);

        data = dataItemsupperBand.toArray(new OHLCDataItem[dataItemsupperBand
                .size()]);
        UpperBand = new DefaultOHLCDataset(StockChart.getStockSymbol(), data);

    }

    /**
     * @return the UpperBand
     */
    public static XYDataset getUpperBand() {
        return UpperBand;
    }

    /**
     * @param aUpperBand the UpperBand to set
     */
    public static void setUpperBand(XYDataset aUpperBand) {
        UpperBand = aUpperBand;
    }

    /**
     * @return the LowerBand
     */
    public static XYDataset getLowerBand() {
        return LowerBand;
    }

    /**
     * @param aLowerBand the LowerBand to set
     */
    public static void setLowerBand(XYDataset aLowerBand) {
        LowerBand = aLowerBand;
    }

    /**
     * @return the dataItemsupperBand
     */
    public static List<OHLCDataItem> getDataItemsupperBand() {
        return dataItemsupperBand;
    }

    /**
     * @param aDataItemsupperBand the dataItemsupperBand to set
     */
    public static void setDataItemsupperBand(List<OHLCDataItem> aDataItemsupperBand) {
        dataItemsupperBand = aDataItemsupperBand;
    }

    /**
     * @return the dataItemslowerBand
     */
    public static List<OHLCDataItem> getDataItemslowerBand() {
        return dataItemslowerBand;
    }

    /**
     * @param aDataItemslowerBand the dataItemslowerBand to set
     */
    public static void setDataItemslowerBand(List<OHLCDataItem> aDataItemslowerBand) {
        dataItemslowerBand = aDataItemslowerBand;
    }
}
