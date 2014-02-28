/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.dao.IStockDAO;
import com.PortfolioManager.domain.impl.StockDAO;
import com.PortfolioManager.businessGUI.chartTools.StockChart;
import com.PortfolioManager.businessGUI.portfolioManagement.performance.data.StockDataUtilities;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ahmed
 */
@Entity
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String symbol;
    private String name;
    private double buyPrice;
    private double currentPrice;
    private double stockPotentialEarnings;
    private double beta;
    @Temporal(TemporalType.DATE)
    private Date betaLatest;
    @OneToOne(fetch = FetchType.EAGER)
    private OrderStock orderStock;

    public Stock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBetaLatest() {
        return betaLatest;
    }

    public void setBetaLatest(Date betaLatest) {
        this.betaLatest = betaLatest;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the beta
     */
    public double getBeta() {
        return beta;
    }

    /**
     * @param beta the beta to set
     */
    public void setBeta(double beta) {
        this.beta = beta;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the buyPrice
     */
    public double getBuyPrice() {
        return buyPrice;
    }

    /**
     * @param buyPrice the buyPrice to set
     */
    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    /**
     * @return the currentPrice
     */
    public double getCurrentPrice() {
        calculateStockPotentialEarnings();
        return currentPrice;
    }

    /**
     * @param currentPrice the currentPrice to set
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void calculateStockPotentialEarnings() {
        IStockDAO iStockDAO = new StockDAO();
        double newPrice = StockChart.getStockLatestPrice(this);
        if (currentPrice != newPrice) {
            currentPrice = newPrice;
            setStockPotentialEarnings(currentPrice - buyPrice);
            iStockDAO.update(this);
        }
    }

    /**
     * @return the orderStock
     */
    public OrderStock getOrderStock() {
        return orderStock;
    }

    /**
     * @param orderStock the orderStock to set
     */
    public void setOrderStock(OrderStock orderStock) {
        this.orderStock = orderStock;
    }

    /**
     * @return the stockPotentialEarnings
     */
    public double getStockPotentialEarnings() {
        calculateStockPotentialEarnings();
        return stockPotentialEarnings;
    }

    /**
     * @param stockPotentialEarnings the stockPotentialEarnings to set
     */
    public void setStockPotentialEarnings(double stockPotentialEarnings) {
        this.stockPotentialEarnings = stockPotentialEarnings;
    }

    public double calculateMonthlyBeta(String marketSymbol) {
        IStockDAO iStockDAO=new StockDAO();
        if (sixDaysDateCompare(betaLatest, new Date())) {
            System.out.println("Calculating Beta for " + name + " stock...");
            downloadMonthlyBeta(marketSymbol);
            List<Stock>list=(List<Stock>)iStockDAO.getStocks(symbol);
            for(Stock s:list)
            {
                s.setBeta(beta);
                s.setBetaLatest(new Date());
                iStockDAO.update(s);
            }
        }
        return beta;
    }

    private boolean sixDaysDateCompare(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return true;
        }
        d2.setDate(d2.getDate() - 6);
        if (d1.getDate() <= d2.getDate()) {
            return true;
        }
        return false;
    }

    private void downloadMonthlyBeta(String marketSymbol) {
        dateManagerForStockChart();
        StockChart.getMe().setXyDataSetFromYahoo();
        StockDataUtilities.getMe().setTypicalValues(StockChart.getMe().getTypicalPrice());
        StockDataUtilities.getMe().setYields();

        setBeta(StockDataUtilities.calculateBeta(marketSymbol));
        setBetaLatest(new Date());
    }

    public void dateManagerForStockChart() {
        Date dateToDay = new Date();
        Date dateMonthAgo = new Date();

        // yahoo specifications
        dateToDay.setMonth(dateToDay.getMonth() - 1);
        dateMonthAgo.setMonth(dateMonthAgo.getMonth() - 2);

        // yahoo specifications

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String date = dateFormat.format(dateToDay);

        StringTokenizer st = new StringTokenizer(date, "-");

        date = dateFormat.format(dateMonthAgo);

        StringTokenizer st2 = new StringTokenizer(date, "-");

        StockChart.getMe().setStockPriceDataRequirement(
                symbol, st2.nextToken(), st2.nextToken(), st2.nextToken(),
                st.nextToken(), st.nextToken(), st.nextToken());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.domain.entities.Stock[ id=" + id + " ]";
    }
}
