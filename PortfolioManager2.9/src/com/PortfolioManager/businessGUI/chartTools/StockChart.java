package com.PortfolioManager.businessGUI.chartTools;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;

import com.PortfolioManager.domain.entities.Stock;
import java.util.Vector;

/**
 *
 * @author Ahmed
 */
public final class StockChart {

    private static double close;
    private static DateAxis time;
    private static NumberAxis price;
    private static CandlestickRenderer renderer;
    private static XYDataset xyDataSet;
    private static XYPlot mainPlot;
    private static List<OHLCDataItem> dataItems;
    private static Vector<Double> typicalPrice;
    private static Vector<Date> typicalPriceDate;
    private static Vector<Double> typicalPriceVolume;
    private static String stockSymbol;
    private static String startMonth;
    private static String startDay;
    private static String startYear;
    private static String endMonth;
    private static String endDay;
    private static String endYear;
    /**
     * @return the typicalPriceVolume
     */
    public Color color;
    private static StockChart stockChart;

    public static StockChart getMe() {
        if (stockChart == null) {
            stockChart = new StockChart();
            return stockChart;
        }
        return stockChart;
    }

    private StockChart() {
        time = new DateAxis("Date");
        time.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());

        price = new NumberAxis("Price");
        price.setAutoRangeIncludesZero(false);

        renderer = new CandlestickRenderer();
        renderer.setSeriesPaint(0, Color.GRAY);
        renderer.setDrawVolume(false);
        renderer.setUpPaint(new Color(47, 208, 255));
        renderer.setDownPaint(Color.RED);


    }

    public static void setStockPriceDataRequirement(String stockSymbol,
            String startMonth, String startDay, String startYear,
            String endMonth, String endDay, String endYear) {
        setStockSymbol(stockSymbol);
        setStartMonth(startMonth);
        setStartDay(startDay);
        setStartYear(startYear);
        setEndMonth(endMonth);
        setEndDay(endDay);
        setEndYear(endYear);
    }

    public static void setXyDataSetFromYahoo() {
        try {
            // the url to import stock data
            String yahoo = "http://ichart.finance.yahoo.com/table.csv?";// fixed
            String strSpecific = "s=" + getStockSymbol() + "&a="
                    + getStartMonth() + "&b=" + getStartDay() + "&c="
                    + getStartYear() + "&d=" + getEndMonth() + "&e="
                    + getEndDay() + "&f=" + getEndYear() + "&ignore=.csv";// variable
            String strUrl = yahoo + strSpecific;
            // end the url to get stock data
            URL url = new URL(strUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            DateFormat df = new SimpleDateFormat("y-M-d");


            double TPrice;
            dataItems = new ArrayList<OHLCDataItem>();
            typicalPrice = new Vector<Double>();
            typicalPriceDate = new Vector<Date>();
            typicalPriceVolume = new Vector<Double>();
            
            String inputLine;
            in.readLine();
            
            while ((inputLine = in.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(inputLine, ",");

                Date date = df.parse(st.nextToken());
                double open = Double.parseDouble(st.nextToken());
                double high = Double.parseDouble(st.nextToken());
                double low = Double.parseDouble(st.nextToken());
                close = Double.parseDouble(st.nextToken());
                double volume = Double.parseDouble(st.nextToken());
                // ******
                // double adjClose = Double.parseDouble(st.nextToken());
                // ******

                TPrice = (high + low + close) / 3;
                getTypicalPrice().add(TPrice);
                getTypicalPriceDate().add(date);
                getTypicalPriceVolume().add(volume);

                OHLCDataItem item = new OHLCDataItem(date, open, high, low,
                        close, volume);
                dataItems.add(item);

            }
            in.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Check your connection", JOptionPane.ERROR_MESSAGE);
        }
        // Yahoo provides datum from new to old
        Collections.reverse(dataItems);
        Collections.reverse(typicalPrice);
        Collections.reverse(typicalPriceDate);
        Collections.reverse(typicalPriceVolume);

        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
        close = data[dataItems.size() - 1].getClose().doubleValue();
        xyDataSet = new DefaultOHLCDataset(stockSymbol, data);
    }

    public static double getStockLatestPrice(Stock s) {

        Date date2DaysBefore = new Date();
        Date dateNow = new Date();
        date2DaysBefore.setDate(dateNow.getDate() - 5);// the stock market is
        // closed from Saturday
        // to Sunday
        // and so we guarantee that we will get the latest price

        // yahoo specifications
        date2DaysBefore.setMonth(date2DaysBefore.getMonth() - 1);
        dateNow.setMonth(dateNow.getMonth() - 1);
        // yahoo specifications

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

        String dat = dateFormat.format(date2DaysBefore);
        StringTokenizer st = new StringTokenizer(dat, "-");

        dat = dateFormat.format(dateNow);
        StringTokenizer st2 = new StringTokenizer(dat, "-");

        setStockPriceDataRequirement(s.getSymbol(), st.nextToken(),
                st.nextToken(), st.nextToken(), st2.nextToken(),
                st2.nextToken(), st2.nextToken());
        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();

        try {
            // the url to import stock data
            String yahoo = "http://ichart.finance.yahoo.com/table.csv?";// fix
            String strSpecific = "s=" + getStockSymbol() + "&a="
                    + getStartMonth() + "&b=" + getStartDay() + "&c="
                    + getStartYear() + "&d=" + getEndMonth() + "&e="
                    + getEndDay() + "&f=" + getEndYear() + "&ignore=.csv";// variable
            String strUrl = yahoo + strSpecific;
            // end the url to get stock data
            URL url = new URL(strUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            DateFormat df = new SimpleDateFormat("y-M-d");

            String inputLine;
            in.readLine();
            while ((inputLine = in.readLine()) != null) {
                st = new StringTokenizer(inputLine, ",");

                Date date = df.parse(st.nextToken());
                double open = Double.parseDouble(st.nextToken());
                double high = Double.parseDouble(st.nextToken());
                double low = Double.parseDouble(st.nextToken());
                close = Double.parseDouble(st.nextToken());
                double volume = Double.parseDouble(st.nextToken());
                // ******
                // double adjClose = Double.parseDouble(st.nextToken());
                // ******

                OHLCDataItem item = new OHLCDataItem(date, open, high, low,
                        close, volume);
                dataItems.add(item);
            }
            in.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Check your connection", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        // Yahoo provides datum from new to old

        Collections.reverse(dataItems);

        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems
                .size()]);
        close = data[dataItems.size() - 1].getClose().doubleValue();
        return close;
    }

    public static DateAxis getTime() {
        return time;
    }

    public static void setTime(DateAxis time) {
        StockChart.time = time;
    }

    public static NumberAxis getPrice() {
        return price;
    }

    public static void setPrice(NumberAxis price) {
        StockChart.price = price;
    }

    public static CandlestickRenderer getRenderer() {
        return renderer;
    }

    public static void setRenderer(CandlestickRenderer renderer) {
        StockChart.renderer = renderer;
    }

    public static XYDataset getXyDataSet() {
        return xyDataSet;
    }

    public static void setXyDataSet(XYDataset xyDataSet) {
        StockChart.xyDataSet = xyDataSet;
    }

    public static XYPlot getMainPlot() {
        return mainPlot;
    }

    public static void setMainPlot() {
        mainPlot = new XYPlot(xyDataSet, getTime(), getPrice(), getRenderer());
        mainPlot.setBackgroundPaint(Color.black);
    }

    public static String getStockSymbol() {
        return stockSymbol;
    }

    public static void setStockSymbol(String stockSymbol) {
        StockChart.stockSymbol = stockSymbol;
    }

    public static String getStartMonth() {
        return startMonth;
    }

    public static void setStartMonth(String startMonth) {
        StockChart.startMonth = startMonth;
    }

    public static String getStartDay() {
        return startDay;
    }

    public static void setStartDay(String startDay) {
        StockChart.startDay = startDay;
    }

    public static String getStartYear() {
        return startYear;
    }

    public static void setStartYear(String startYear) {
        StockChart.startYear = startYear;
    }

    public static String getEndMonth() {
        return endMonth;
    }

    public static void setEndMonth(String endMonth) {
        StockChart.endMonth = endMonth;
    }

    public static String getEndDay() {
        return endDay;
    }

    public static void setEndDay(String endDay) {
        StockChart.endDay = endDay;
    }

    public static String getEndYear() {
        return endYear;
    }

    public static void setEndYear(String endYear) {
        StockChart.endYear = endYear;
    }

    public static double getClose() {
        return close;
    }

    /**
     * @return the dataItems
     */
    public static List<OHLCDataItem> getDataItems() {
        return dataItems;
    }

    /**
     * @param aDataItems the dataItems to set
     */
    public static void setDataItems(List<OHLCDataItem> aDataItems) {
        dataItems = aDataItems;
    }

    /**
     * @return the typicalPrice
     */
    public static Vector<Double> getTypicalPrice() {
        return typicalPrice;
    }

    /**
     * @param aTypicalPrice the typicalPrice to set
     */
    public static void setTypicalPrice(Vector<Double> aTypicalPrice) {
        typicalPrice = aTypicalPrice;
    }

    /**
     * @return the typicalPriceDate
     */
    public static Vector<Date> getTypicalPriceDate() {
        return typicalPriceDate;
    }

    /**
     * @param aTypicalPriceDate the typicalPriceDate to set
     */
    public static void setTypicalPriceDate(Vector<Date> aTypicalPriceDate) {
        typicalPriceDate = aTypicalPriceDate;
    }

    public static Vector<Double> getTypicalPriceVolume() {
        return typicalPriceVolume;
    }

    /**
     * @param aTypicalPriceVolume the typicalPriceVolume to set
     */
    public static void setTypicalPriceVolume(Vector<Double> aTypicalPriceVolume) {
        typicalPriceVolume = aTypicalPriceVolume;
    }
}
