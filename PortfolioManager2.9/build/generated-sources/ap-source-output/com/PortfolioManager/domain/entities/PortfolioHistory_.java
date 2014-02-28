package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.entities.Portfolio;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T22:52:28")
@StaticMetamodel(PortfolioHistory.class)
public class PortfolioHistory_ { 

    public static volatile SingularAttribute<PortfolioHistory, Long> id;
    public static volatile SingularAttribute<PortfolioHistory, Double> portfolioTodayValue;
    public static volatile SingularAttribute<PortfolioHistory, Double> portfolioNetAdditions;
    public static volatile SingularAttribute<PortfolioHistory, Double> portfolioReturn;
    public static volatile SingularAttribute<PortfolioHistory, Double> portfolioBeta;
    public static volatile SingularAttribute<PortfolioHistory, Double> portfolioPerformance;
    public static volatile SingularAttribute<PortfolioHistory, Double> RIRate;
    public static volatile SingularAttribute<PortfolioHistory, Portfolio> portfolio;
    public static volatile SingularAttribute<PortfolioHistory, Date> monthOfReturn;
    public static volatile SingularAttribute<PortfolioHistory, Double> portfolioInitalMoney;

}