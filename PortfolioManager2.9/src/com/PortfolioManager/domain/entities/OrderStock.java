/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.dao.IOrderStock;
import com.PortfolioManager.domain.dao.IStockDAO;
import com.PortfolioManager.domain.impl.OrderStockDAO;
import com.PortfolioManager.domain.impl.StockDAO;
import java.io.Serializable;
import java.util.Date;
import java.util.StringTokenizer;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Ahmed
 */
@Entity
public class OrderStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderID;
    @ManyToOne(fetch = FetchType.EAGER)
    private Portfolio portfolio;
    @OneToOne(cascade = CascadeType.ALL)
    private Stock stock;
    private int stocksNumber;
    private double price;
    private double potentialEarnings;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateFirst;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateUpdate;// key to fast update behaviour ;)

    public OrderStock() {
    }

    public OrderStock(int stocksNumber, Stock stock) {
        super();
        this.stocksNumber = stocksNumber;
        this.stock = stock;
        this.dateFirst = new Date();
        this.dateUpdate = new Date();
        price = this.stock.getBuyPrice() * stocksNumber;
    }

    public static String displaymoney(double m) {
        String monney;
        StringTokenizer st = new StringTokenizer("" + m, ".");
        monney = st.nextToken();
        String comma = st.nextToken();

        int l = comma.length();
        if (l > 2) {
            l = 2;
        }

        monney = monney + "." + comma.substring(0, l);
        return monney;
    }

    public void calculateEarnings() {
        if (dateUpdate.getDay() != new Date().getDay()) {
            potentialEarnings = stock.getStockPotentialEarnings() * stocksNumber;
            dateUpdate = new Date();
            stock.setOrderStock(this);
            IOrderStock iOrderStock = new OrderStockDAO();
            iOrderStock.update(this);

        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getOrderID() != null ? getOrderID().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the orderID fields are not set
        if (!(object instanceof OrderStock)) {
            return false;
        }
        OrderStock other = (OrderStock) object;
        if ((this.getOrderID() == null && other.getOrderID() != null) || (this.getOrderID() != null && !this.orderID.equals(other.orderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\n------------------------------\nAccount Number: "
                + getPortfolio().getId() + "\nOrder number: " + getOrderID()
                + "\nDate: " + getDateFirst().toString() + "\nStock: " + getStock()
                + "\n Quantity: " + getStocksNumber();
    }

    /**
     * @return the orderID
     */
    public Long getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    /**
     * @return the portfolio
     */
    public Portfolio getPortfolio() {
        return portfolio;
    }

    /**
     * @param portfolio the portfolio to set
     */
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    /**
     * @return the stock
     */
    public Stock getStock() {
        return stock;
    }
    
    
    //portfolio performance routine
    public Stock getUpdatedStock() {
        IStockDAO isdao = new StockDAO();
        stock = isdao.getStockById(stock.getId());
        return stock;
    }//portfolio performance routine

    /**
     * @param stock the stock to set
     */
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    /**
     * @return the stocksNumber
     */
    public int getStocksNumber() {
        return stocksNumber;
    }

    /**
     * @param stocksNumber the stocksNumber to set
     */
    public void setStocksNumber(int stocksNumber) {
        this.stocksNumber = stocksNumber;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the potentialEarnings
     */
    public double getPotentialEarnings() {
        calculateEarnings();
        return potentialEarnings;
    }

    /**
     * @param potentialEarnings the potentialEarnings to set
     */
    public void setPotentialEarnings(double potentialEarnings) {
        this.potentialEarnings = potentialEarnings;
    }

    /**
     * @return the dateFirst
     */
    public Date getDateFirst() {
        return dateFirst;
    }

    /**
     * @param dateFirst the dateFirst to set
     */
    public void setDateFirst(Date dateFirst) {
        this.dateFirst = dateFirst;
    }

    /**
     * @return the dateUpdate
     */
    public Date getDateUpdate() {
        return dateUpdate;
    }

    /**
     * @param dateUpdate the dateUpdate to set
     */
    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
