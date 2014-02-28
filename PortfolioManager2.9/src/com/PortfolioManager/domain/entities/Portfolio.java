/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.dao.IOrderStock;
import com.PortfolioManager.domain.dao.IPortfolioHistory;
import com.PortfolioManager.domain.impl.OrderStockDAO;
import com.PortfolioManager.domain.impl.PortfolioHistoryDAO;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Ahmed
 */
@Entity
public class Portfolio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
    private Collection<OrderStock> orderStocks;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
    private Collection<PortfolioOperation> operations;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
    private Collection<PortfolioHistory> portfolioHistory;
    @OneToOne
    private Market market;
    private double potentialEarnings;
    private double initialMoney;
    private double liquidMoney;
    private double portfolioBeta;
    private double portfolioPerformance;
    private double portfolioToDayValue;
    private String name;
    private double RIRate;
    private double investedMoney;
    private double portfolioReturn;

    public double getRIRate() {
        return RIRate;
    }

    public void setRIRate(double RIRate) {
        this.RIRate = RIRate;
    }
    

    public double getPortfolioToDayValue() {
        return portfolioToDayValue;
    }

    public void setPortfolioToDayValue(double portfolioToDayValue) {
        this.portfolioToDayValue = portfolioToDayValue;
    }

    public Collection<PortfolioHistory> getPortfolioHistory() {
        return portfolioHistory;
    }

    public void setPortfolioHistory(Collection<PortfolioHistory> portfolioHistory) {
        this.portfolioHistory = portfolioHistory;
    }

    public double getPortfolioReturn() {
        return portfolioReturn;
    }

    public void setPortfolioReturn(double portfolioReturn) {
        this.portfolioReturn = portfolioReturn;
    }

    public double getPortfolioBeta() {
        return portfolioBeta;
    }

    public void setPortfolioBeta(double portfolioBeta) {
        this.portfolioBeta = portfolioBeta;
    }

    public Portfolio() {
    }

    public Portfolio(double lquidMoney) {
        orderStocks = new HashSet<OrderStock>();
        operations = new HashSet<PortfolioOperation>();
        this.liquidMoney = lquidMoney;
        this.initialMoney = liquidMoney;
        potentialEarnings = 0.0;
        investedMoney = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Collection<OrderStock> getOrders() {
        return orderStocks;
    }

    public void setOrderStocks(Collection<OrderStock> orderStocks) {
        this.orderStocks = orderStocks;
    }

    public void addOrderStock(OrderStock orderStock) {
        orderStock.setPortfolio(this);
        orderStocks.add(orderStock);
        investedMoney += orderStock.getPrice();
        liquidMoney -= orderStock.getPrice();
    }

    public void removeOrderStock(OrderStock orderStock) {
        //still more 
        orderStocks.remove(orderStock);
        investedMoney -= orderStock.getPrice();
        liquidMoney += orderStock.getPrice();
        liquidMoney += orderStock.getPotentialEarnings();
        IOrderStock iOrderStock = new OrderStockDAO();
        iOrderStock.delete(orderStock.getOrderID());
    }

    public Collection<PortfolioOperation> getOperations() {
        return operations;
    }

    public void setOperations(Collection<PortfolioOperation> operations) {
        this.operations = operations;
    }

    public void addOperation(PortfolioOperation operation) {
        operation.setPortfolio(this);
        operations.add(operation);
    }

    public void addPortfolioHistory(PortfolioHistory portfolioHistory) {
        portfolioHistory.setPortfolio(this);
        this.portfolioHistory.add(portfolioHistory);
    }

    public PortfolioHistory getPortfolioHistoryForYearAndMonth() {
        IPortfolioHistory iPortfolioHistory = new PortfolioHistoryDAO();
        return iPortfolioHistory.getHistoryByYearAndMonth(this.id);
    }

    /**
     * @return the potentialEarnings
     */
    public double getPotentialEarnings() {

        return potentialEarnings;
    }

    /**
     * @param potentialEarnings the potentialEarnings to set
     */
    public void setPotentialEarnings(double potentialEarnings) {
        this.potentialEarnings = potentialEarnings;
    }

    /**
     * @return the market
     */
    public Market getMarket() {
        return market;
    }

    /**
     * @param market the market to set
     */
    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     * @return the liquidMoney
     */
    public double getLiquidMoney() {
        return liquidMoney;
    }

    /**
     * @param liquidMoney the liquidMoney to set
     */
    public void setLiquidMoney(double liquidMoney) {
        this.liquidMoney = liquidMoney;
    }

    /**
     * @return the investedMoney
     */
    public double getInvestedMoney() {
        return investedMoney;
    }

    /**
     * @param investedMoney the investedMoney to set
     */
    public void setInvestedMoney(double investedMoney) {
        this.investedMoney = investedMoney;
    }

    /**
     * @return the portfolioPerformance
     */
    public double getPortfolioPerformance() {
        return portfolioPerformance;
    }

    /**
     * @param portfolioPerformance the portfolioPerformance to set
     */
    public void setPortfolioPerformance(double portfolioPerformance) {
        this.portfolioPerformance = portfolioPerformance;
    }

    public double calculateEarnings() {
        setPotentialEarnings(0);
        if (!orderStocks.isEmpty()) {
            Iterator<OrderStock> it = orderStocks.iterator();
            double sum = 0.0;
            while (it.hasNext()) {
                OrderStock orderStock = it.next();
                sum += orderStock.getPotentialEarnings();
//			System.out.println(orderStock.getReturns()+" "+orderStock.getPrice());
            }
            setPotentialEarnings(sum);
        }
        return getPotentialEarnings();

    }

    public double calculateToDayValue() {
        double v = 0;
        calculateEarnings();
        v += getLiquidMoney() + getInvestedMoney() + getPotentialEarnings();
        setPortfolioToDayValue(v);
        return v;

    }

    public String displayMoney(double m) {
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

    public void credit(double money) {
        liquidMoney += money;

    }

    public void debit(double money) {
        liquidMoney -= money;
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
        if (!(object instanceof Portfolio)) {
            return false;
        }
        Portfolio other = (Portfolio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Portfolio{" + "id=" + id + ", account=" + account + ", returnValue=" + potentialEarnings + ", liquidMoney=" + liquidMoney + ", investedMoney=" + investedMoney + ", performance=" + portfolioPerformance + ", name=" + name + ", market=" + market + '}';
    }

    /**
     * @return the initialMoney
     */
    public double getInitialMoney() {
        return initialMoney;
    }

    /**
     * @param initialMoney the initialMoney to set
     */
    public void setInitialMoney(double initialMoney) {
        this.initialMoney = initialMoney;
    }
}
