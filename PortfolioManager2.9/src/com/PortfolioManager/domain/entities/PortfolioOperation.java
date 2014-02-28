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
public class PortfolioOperation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double amount;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date operationDate;

    @ManyToOne(fetch= FetchType.EAGER)
    private Portfolio portfolio;

    public PortfolioOperation() {
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

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the operationDate
     */
    public Date getOperationDate() {
        return operationDate;
    }

    /**
     * @param operationDate the operationDate to set
     */
    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 79 * hash + (this.operationDate != null ? this.operationDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PortfolioOperation other = (PortfolioOperation) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (this.operationDate != other.operationDate && (this.operationDate == null || !this.operationDate.equals(other.operationDate))) {
            return false;
        }
        if (this.portfolio != other.portfolio && (this.portfolio == null || !this.portfolio.equals(other.portfolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Operation{" + "id=" + id + ", amount=" + amount + ", date=" + operationDate + ", portfolio=" + portfolio + '}';
    }

    
        
    
}
