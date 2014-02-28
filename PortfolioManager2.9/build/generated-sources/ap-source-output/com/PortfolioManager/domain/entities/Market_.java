package com.PortfolioManager.domain.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T22:52:28")
@StaticMetamodel(Market.class)
public class Market_ { 

    public static volatile SingularAttribute<Market, String> marketId;
    public static volatile SingularAttribute<Market, Date> indiceLatest;
    public static volatile SingularAttribute<Market, Double> indice;
    public static volatile SingularAttribute<Market, Double> monthlyVariance;
    public static volatile SingularAttribute<Market, Double> oldIndice;
    public static volatile SingularAttribute<Market, String> marketSymbol;
    public static volatile SingularAttribute<Market, Date> varianceLatest;

}