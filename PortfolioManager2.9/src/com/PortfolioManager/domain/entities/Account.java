/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.dao.IPortfolioDAO;
import com.PortfolioManager.domain.impl.PortfolioDAO;
import java.io.Serializable;
import java.util.Collection;
import java.util.StringTokenizer;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Ahmed
 */
@Entity
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private double money;
    private double investedMoney;
    private double initialMoney;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<Portfolio> portfolios;
    
    private long selectedPortfolio;

    @OneToOne(mappedBy="account")
    private Investor investor;

    public Account() {
    }
            
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    
    
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Collection<Portfolio> getPortfolios() {
        return portfolios;
    }
    
    public Portfolio getPortfolio()
    {
        IPortfolioDAO iPortfolioDAO=new PortfolioDAO();
        Portfolio portfolio=iPortfolioDAO.getPortfolioById(selectedPortfolio);
        return portfolio;
    }

    public void setPortfolios(Collection<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    public void addPortfolio(Portfolio portfolio) {
        portfolio.setAccount(this);
        IPortfolioDAO iPortfolioDAO = new PortfolioDAO();
        iPortfolioDAO.persist(portfolio);
        
        double aux=portfolio.getLiquidMoney();
        
        setSelectedPortfolio(portfolio.getId());
        investedMoney+=aux;
        money-=aux;
        selectedPortfolio=portfolio.getId();
        portfolios.add(portfolio);
    }

    public void removePortfolio(Long id) {
        IPortfolioDAO iPortfolioDAO=new PortfolioDAO();
        Portfolio p=iPortfolioDAO.getPortfolioById(id);
        portfolios.remove(p);
        iPortfolioDAO.delete(p);
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.domain.entities.Account[ id=" + id + " ]";
    }
    
    public String displayMoney() {
		String monney;
		StringTokenizer st = new StringTokenizer("" + money, ".");
		monney = st.nextToken();
		String comma = st.nextToken();
		
		int l = comma.length();
		if (l > 2)
			l = 2;
		
		monney = monney + "." + comma.substring(0, l);
		return monney;
	}

    /**
     * @return the selectedPortfolio
     */
    public long getSelectedPortfolio() {
        return selectedPortfolio;
    }

    /**
     * @param selectedPortfolio the selectedPortfolio to set
     */
    public void setSelectedPortfolio(long selectedPortfolio) {
        this.selectedPortfolio = selectedPortfolio;
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
    
    public void credit(double money){
        this.money+=money;
        investedMoney-=money;
    }
    public void debit(double money)
    {
        this.money-=money;
        investedMoney+=money;
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
