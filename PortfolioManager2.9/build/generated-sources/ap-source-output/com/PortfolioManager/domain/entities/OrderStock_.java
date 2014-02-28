package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.entities.Portfolio;
import com.PortfolioManager.domain.entities.Stock;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T22:52:28")
@StaticMetamodel(OrderStock.class)
public class OrderStock_ { 

    public static volatile SingularAttribute<OrderStock, Long> orderID;
    public static volatile SingularAttribute<OrderStock, Stock> stock;
    public static volatile SingularAttribute<OrderStock, Double> price;
    public static volatile SingularAttribute<OrderStock, Double> potentialEarnings;
    public static volatile SingularAttribute<OrderStock, Date> dateUpdate;
    public static volatile SingularAttribute<OrderStock, Portfolio> portfolio;
    public static volatile SingularAttribute<OrderStock, Date> dateFirst;
    public static volatile SingularAttribute<OrderStock, Integer> stocksNumber;

}