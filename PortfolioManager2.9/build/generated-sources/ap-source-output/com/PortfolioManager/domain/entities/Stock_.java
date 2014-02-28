package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.entities.OrderStock;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T22:52:28")
@StaticMetamodel(Stock.class)
public class Stock_ { 

    public static volatile SingularAttribute<Stock, Long> id;
    public static volatile SingularAttribute<Stock, String> symbol;
    public static volatile SingularAttribute<Stock, Date> betaLatest;
    public static volatile SingularAttribute<Stock, String> name;
    public static volatile SingularAttribute<Stock, Double> buyPrice;
    public static volatile SingularAttribute<Stock, Double> stockPotentialEarnings;
    public static volatile SingularAttribute<Stock, Double> currentPrice;
    public static volatile SingularAttribute<Stock, Double> beta;
    public static volatile SingularAttribute<Stock, OrderStock> orderStock;

}