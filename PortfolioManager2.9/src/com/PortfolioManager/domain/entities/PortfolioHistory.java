/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.domain.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Ahmed
 */
@Entity
public class PortfolioHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Portfolio portfolio;
    private double portfolioInitalMoney;
    private double portfolioTodayValue;
    private double portfolioNetAdditions;
    private double portfolioReturn;
    private double portfolioBeta;
    private double portfolioPerformance;
    private double RIRate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date monthOfReturn;
    
    public double getRIRate() {
        return RIRate;
    }

    public void setRIRate(double RIRate) {
        this.RIRate = RIRate;
    }

    public double getPortfolioTodayValue() {
        return portfolioTodayValue;
    }

    public void setPortfolioTodayValue(double portfolioTodayValue) {
        this.portfolioTodayValue = portfolioTodayValue;
    }

    public double getPortfolioInitalMoney() {
        return portfolioInitalMoney;
    }

    public void setPortfolioInitalMoney(double portfolioInitalMoney) {
        this.portfolioInitalMoney = portfolioInitalMoney;
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

    public double getPortfolioPerformance() {
        return portfolioPerformance;
    }

    public void setPortfolioPerformance(double portfolioPerformance) {
        this.portfolioPerformance = portfolioPerformance;
    }

    public Date getMonthOfReturn() {
        return monthOfReturn;
    }

    public void setMonthOfReturn(Date MonthOfReturn) {
        this.monthOfReturn = MonthOfReturn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public double getPortfolioNetAdditions() {
        return portfolioNetAdditions;
    }

    public void setPortfolioNetAdditions(double portfolioNetAdditions) {
        this.portfolioNetAdditions = portfolioNetAdditions;
    }

    public double calculatePortfolioReturn()
    {
        return ((portfolioTodayValue-portfolioNetAdditions)/portfolioInitalMoney)-1;
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
        if (!(object instanceof PortfolioHistory)) {
            return false;
        }
        PortfolioHistory other = (PortfolioHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.domain.entities.PortfolioHistory[ id=" + id + " ]";
    }
}
