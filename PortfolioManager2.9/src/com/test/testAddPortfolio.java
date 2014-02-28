/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import com.PortfolioManager.domain.dao.IAccountDAO;
import com.PortfolioManager.domain.dao.IInvestorDAO;
import com.PortfolioManager.domain.dao.IMarket;
import com.PortfolioManager.domain.entities.Account;
import com.PortfolioManager.domain.entities.Investor;
import com.PortfolioManager.domain.entities.Market;
import com.PortfolioManager.domain.entities.Portfolio;
import com.PortfolioManager.domain.impl.AccountDAO;
import com.PortfolioManager.domain.impl.InvestorDAO;
import com.PortfolioManager.domain.impl.MarketDAO;
import com.PortfolioManager.utilities.technic.JPA;

/**
 *
 * @author Ahmed
 */
public class testAddPortfolio {

    public static void main(String[] args) {
        JPA.setPersistenceUnit("PortM");
        IInvestorDAO iInvestorDAO = new InvestorDAO();
        Investor investor = iInvestorDAO.getInvestorById("ahmed");
        IAccountDAO iadao = new AccountDAO();
        Account account = iadao.getAccountById(investor.getAccount().getId());
//        Portfolio portfolio = new Portfolio();
//        
//        IMarket iMarket=new MarketDAO();
//        Market market=iMarket.getMarketById("France_Paris_CAC40");
//        portfolio.setMarket(market);
//        
//        account.addPortfolio(portfolio);
        
        account.setSelectedPortfolio(51l);
        iadao.update(account);
    }
}
