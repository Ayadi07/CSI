package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.entities.Investor;
import com.PortfolioManager.domain.entities.Portfolio;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T22:52:28")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, Long> id;
    public static volatile CollectionAttribute<Account, Portfolio> portfolios;
    public static volatile SingularAttribute<Account, Long> selectedPortfolio;
    public static volatile SingularAttribute<Account, Double> money;
    public static volatile SingularAttribute<Account, Investor> investor;
    public static volatile SingularAttribute<Account, Double> investedMoney;
    public static volatile SingularAttribute<Account, Double> initialMoney;

}