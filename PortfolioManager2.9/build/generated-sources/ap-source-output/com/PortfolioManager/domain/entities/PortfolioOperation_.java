package com.PortfolioManager.domain.entities;

import com.PortfolioManager.domain.entities.Portfolio;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-21T22:52:28")
@StaticMetamodel(PortfolioOperation.class)
public class PortfolioOperation_ { 

    public static volatile SingularAttribute<PortfolioOperation, Long> id;
    public static volatile SingularAttribute<PortfolioOperation, Double> amount;
    public static volatile SingularAttribute<PortfolioOperation, Portfolio> portfolio;
    public static volatile SingularAttribute<PortfolioOperation, Date> operationDate;

}