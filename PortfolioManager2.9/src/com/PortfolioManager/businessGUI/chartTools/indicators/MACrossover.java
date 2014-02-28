/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.businessGUI.chartTools.indicators;

import com.PortfolioManager.businessGUI.chartTools.StockChart;
import java.awt.Color;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Ahmed
 */
public class MACrossover {

    private static boolean selected = false;

    public static void changeState() {
        selected = !selected;
    }

    public static boolean isSelected() {
        return selected;
    }
    public static void setIndicator(boolean b) {
        final long ONE_DAY = 24 * 60 * 60 * 1000;
        if (b) {
            XYLineAndShapeRenderer maRenderer1 = new XYLineAndShapeRenderer(
                    true, false);

            maRenderer1.setSeriesPaint(0, Color.cyan);


            XYDataset movingAverageCourtTerm = MovingAverage
                    .createMovingAverage(StockChart.getXyDataSet(), "Simple Moving Average 14",
                    14 * ONE_DAY, 0);
            
            StockChart.getMainPlot().setRenderer(1, maRenderer1);
            StockChart.getMainPlot().setDataset(1, movingAverageCourtTerm);

            XYLineAndShapeRenderer maRenderer2 = new XYLineAndShapeRenderer(
                    true, false);
//			maRenderer2.setSeriesStroke(0, new BasicStroke(2));
            maRenderer2.setSeriesPaint(0, Color.red);
            XYDataset movingAverageLongTerm = MovingAverage
                    .createMovingAverage(StockChart.getXyDataSet(), "Simple Moving Average 28",
                    28 * ONE_DAY, 0);
            
            StockChart.getMainPlot().setRenderer(2, maRenderer2);
            StockChart.getMainPlot().setDataset(2, movingAverageLongTerm);
//			mainPlot.setBackgroundPaint(Color.gray);
        } else {
            StockChart.getMainPlot().setRenderer(1, null);
            StockChart.getMainPlot().setRenderer(2, null);
            StockChart.getMainPlot().setDataset(2, null);
            StockChart.getMainPlot().setDataset(1, null);
        }
    }
}
