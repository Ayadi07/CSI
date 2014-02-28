package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.entities.Account;
import com.PortfolioManager.domain.entities.Market;
import com.PortfolioManager.domain.entities.OrderStock;
import com.PortfolioManager.domain.entities.PortfolioHistory;
import com.PortfolioManager.domain.entities.PortfolioOperation;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T22:52:28")
@StaticMetamodel(Portfolio.class)
public class Portfolio_ { 

    public static volatile CollectionAttribute<Portfolio, OrderStock> orderStocks;
    public static volatile SingularAttribute<Portfolio, Double> liquidMoney;
    public static volatile SingularAttribute<Portfolio, Double> portfolioBeta;
    public static volatile SingularAttribute<Portfolio, Double> portfolioToDayValue;
    public static volatile CollectionAttribute<Portfolio, PortfolioHistory> portfolioHistory;
    public static volatile SingularAttribute<Portfolio, Double> initialMoney;
    public static volatile SingularAttribute<Portfolio, Long> id;
    public static volatile SingularAttribute<Portfolio, Market> market;
    public static volatile SingularAttribute<Portfolio, Double> portfolioReturn;
    public static volatile SingularAttribute<Portfolio, Double> potentialEarnings;
    public static volatile SingularAttribute<Portfolio, Double> RIRate;
    public static volatile SingularAttribute<Portfolio, Double> portfolioPerformance;
    public static volatile SingularAttribute<Portfolio, String> name;
    public static volatile SingularAttribute<Portfolio, Account> account;
    public static volatile SingularAttribute<Portfolio, Double> investedMoney;
    public static volatile CollectionAttribute<Portfolio, PortfolioOperation> operations;

}